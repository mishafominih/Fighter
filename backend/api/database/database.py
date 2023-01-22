import psycopg2
import json

from psycopg2 import sql, extras


def connection_db(func):
    def connect(*args, **kwards):
        conn = psycopg2.connect(f"""
            host=rc1b-e1m47fv71pchwvru.mdb.yandexcloud.net
            port=6432
            dbname=fighter
            user=yukey
            password=postgres
            target_session_attrs=read-write
        """)
        cursor = conn.cursor(cursor_factory=extras.RealDictCursor)
        result = func(cursor, *args, **kwards)

        conn.commit()
        cursor.close()
        conn.close()
        return result

    return connect


@connection_db
def sign_in(cursor, password, login):
    tmpl = """
        SELECT "_id"
        FROM "Users"
        WHERE "Login" = %s and "Password" = %s
        LIMIT 1;"""
    cursor.execute(tmpl, [login, password])
    record = cursor.fetchone()
    if not record:
        return 'Неверный логин или пароль'
    else:
        return {'id': record['_id'], 'result': True}


@connection_db
def registration(cursor, password, login):
    cursor.execute("""SELECT "Login" FROM "Users" WHERE "Login" = %s;""", [login])
    result = cursor.fetchone()
    if result:
        return 'Пользователь с таким логином уже есть'
    tmpl = """INSERT INTO public."Users" ("Login", "Password") VALUES (%s, %s) RETURNING "_id";"""
    cursor.execute(tmpl, [login, password])
    return cursor.fetchone()['_id']


@connection_db
def create_tournament(cursor, **params):
    event_tmpl = """
        INSERT INTO public."Event" ("Name", "Description", "Type", "CountThread", "User", "Categories")
        VALUES  (%s, %s, %s, %s, %s, %s)
        RETURNING "_id";
    """
    cursor.execute(event_tmpl, [params.get('name'), params.get('description'), params.get('count_thread'),
                                params.get('type'), params.get('user_id'), json.dumps(params.get('categories'))])

    return cursor.fetchone()['_id']


@connection_db
def get_tournaments_for_db(cursor, user_id):
    tmpl = """
    SELECT 
        "_id" "key",
        "Name" "name",
        "Description" "description",
        "Type" "type",
        "CountThread" "count_thread",
        "User" "user_id",
        "Categories" "categories",
        "Status" "status"
    FROM "Event"
    WHERE "User" = %s
    """
    cursor.execute(tmpl, [user_id])
    data = cursor.fetchall()
    return data


@connection_db
def add_new_player(cursor, **params):
    player_tmpl = """
            INSERT INTO public."Players" 
            ("Name","Surname","Patronymic","Link","Description","UserId","TournamentId","Categories")
            VALUES  (%s, %s, %s, %s, %s, %s, %s, %s)
            RETURNING "_id";
        """

    cursor.execute(player_tmpl, [
        params.get('name'), params.get('surname'), params.get('patronymic'), params.get('link'),
        params.get('description'), int(params.get('user_id')), int(params.get('tournament_id')),
        json.dumps(params.get('categories'))
    ])

    return cursor.fetchone()['_id']


@connection_db
def get_players_for_tournament(cursor, tournament_id):
    sql = """
        SELECT 
            "_id" as "id"
            , "Name" as "name" 
            , "Surname" as "surname"
            , "Patronymic" as "patronymic"
            , "Link" as "link"
            , "Description" as "description"
            , "Categories" as "categories"
        FROM "Players"
        WHERE "TournamentId" = %s
    """
    cursor.execute(sql, [tournament_id])
    data = cursor.fetchall()
    return data


@connection_db
def create_tournament_grid(cursor, tournament_id):
    places_tmpl = """
        SELECT "Places"
        FROM "Event"
        WHERE "_id" = %s
    """
    cursor.execute(places_tmpl, [tournament_id])
    places = cursor.fetchone()

    players_tmpl = """
        SELECT *
        FROM "Players"
        WHERE "TournamentId" = %s
    """

    cursor.execute(players_tmpl, [tournament_id])


@connection_db
def get_tournament_list(cursor, tournament_id):
    sql = """
        SELECT 
            "id" as "id"
            , "fighterone" as "fighter_one"
            , "fightertwo" as "fighter_two"
            , "winner" as "winner"
            , null as "score"
            , "child" as "child"
            , CASE
                WHEN "third" is true THEN 1
                WHEN "child" is null and "third" is false then 0
              ELSE (SELECT max("stage") + 1
                    FROM "EventTiming"
                    WHERE "tournamentid" = %s) - 
              "stage" END as "stage"
            , "onescore" as "one_score"
            , "twoscore" as "two_score"
        FROM "EventTiming"
        WHERE "tournamentid" = %s
        ORDER BY "stage" desc, "id"
    """
    cursor.execute(sql, [tournament_id] * 2)
    data = cursor.fetchall()
    return data


@connection_db
def get_tournament_grid(cursor, tournament_id):
    sql = """
        SELECT 
            array_agg(COALESCE(rec."one",'') || ',' || COALESCE(rec."two", '')) "Array"
        FROM
        (
            SELECT
                (SELECT "Name" FROM "Players" WHERE "_id" = "fighterone" LIMIT 1) "one",
                (SELECT "Name" FROM "Players" WHERE "_id" = "fightertwo" LIMIT 1) "two",
                "stage"
            FROM "EventTiming"
            WHERE "tournamentid" = %s AND "third" IS FALSE
            ORDER BY "id"
        ) AS rec
        GROUP BY "stage"
        ORDER BY "stage" ASC
    """
    cursor.execute(sql, [tournament_id])
    data = cursor.fetchall()
    result = []
    for val in data:
        stage = []
        for couple in val.get('Array'):
            fighters = couple.split(',')
            stage.append([{'name': fighters[0]}, {'name': fighters[1]}])
        result.append(stage)
    winner = """
        SELECT 
            "Name" "name"
        FROM "EventTiming" event
        JOIN "Players" players ON players."_id" = event."winner"
        WHERE "child" IS NULL AND "TournamentId" = %s AND "third" IS FALSE
        LIMIT 1
    """

    cursor.execute(winner, [tournament_id])
    data = cursor.fetchone()
    if not data:
        data = {'name': ''}
    result.append([[data]])

    return result


@connection_db
def add_new_timing(cursor, params):
    player_tmpl = """
            INSERT INTO public."EventTiming" 
            ("id", "userid","tournamentid","place","fighterone","fightertwo","winner","child","stage","secondchild","third")
            VALUES  (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            RETURNING "id";
        """

    for param in params:
        cursor.execute(player_tmpl, param)

    return cursor.fetchone()['id']


@connection_db
def write_winner(cursor, user_id, tournament_id, fight_id, winner_id, one_score, two_score):
    player_tmpl = """
            UPDATE public."EventTiming" 
            SET "winner" = %s, "onescore" = %s, "twoscore" = %s 
            WHERE 
                "userid" = %s
                AND "tournamentid" = %s
                AND "id" = %s
            RETURNING *
        """

    cursor.execute(player_tmpl, [winner_id, one_score, two_score, user_id, tournament_id, fight_id])
    return cursor.fetchone()


@connection_db
def write_player(cursor, user_id, tournament_id, fight_id, player_id):
    fight_filter = '"third" IS TRUE' if not fight_id else f'"id" = {fight_id}'
    player_tmpl = f"""
                UPDATE public."EventTiming" 
                SET 
                    "fightertwo" = 
                        CASE WHEN "fightertwo" is null AND "fighterone" is not null THEN %s
                    ELSE "fightertwo" END,
                    "fighterone" = 
                        CASE WHEN "fighterone" is null THEN %s
                    ELSE "fighterone" END
                WHERE 
                    "userid" = %s
                    AND "tournamentid" = %s
                    AND {fight_filter}
                RETURNING *
            """.format(fight_filter=fight_filter)

    cursor.execute(player_tmpl, [player_id, player_id, user_id, tournament_id])
    return cursor.fetchone()


@connection_db
def write_status(cursor, user_id, tournament_id, status):
    player_tmpl = """
            UPDATE public."Event" 
            SET "Status" = %s
            WHERE "User" = %s AND "_id" = %s
        """

    cursor.execute(player_tmpl, [status, user_id, tournament_id])


@connection_db
def get_tournament(cursor, user_id, tournament_id):
    player_tmpl = """
            SELECT 
                "_id" "key",
                "Name" "name",
                "Description" "description",
                "Type" "type",
                "CountThread" "count_thread",
                "User" "user_id",
                "Categories" "categories",
                "Status" "status"
            FROM public."Event"
            WHERE "User" = %s AND "_id" = %s
        """

    cursor.execute(player_tmpl, [user_id, tournament_id])
    return cursor.fetchone()


@connection_db
def get_tournament_result(cursor, user_id, tournament_id):
    seconds_place_tmpl = """
        SELECT 
            ev."winner" "first",
            CASE 
                WHEN ev."fighterone" = "winner" THEN ev."fightertwo"
                ELSE ev."fighterone"
            END "second",
            p."Categories"
        FROM "EventTiming" ev
        JOIN "Players" p ON p."_id" = ev."winner"
        WHERE ev."child" IS NULL AND ev."third" IS FALSE
            AND ev."tournamentid" = %s
        ORDER BY "Categories"
    """

    third_place_tmpl = """
        SELECT
            ev."winner" "third",
            p."Categories"
        FROM "EventTiming" ev
        JOIN "Players" p ON p."_id" = ev."winner"
        WHERE ev."child" IS NULL AND ev."third" IS TRUE
            AND ev."tournamentid" = %s
        ORDER BY "Categories"
    """

    cursor.execute(seconds_place_tmpl, [tournament_id])
    seconds_place = cursor.fetchall()

    cursor.execute(third_place_tmpl, [tournament_id])
    third_place = cursor.fetchall()
    winners = []
    places = []
    for i in range(0, len(seconds_place)):
        sec = seconds_place[i]
        th = third_place[i] if i < len(third_place) else {}
        winners += [sec.get('first'), sec.get('second'), th.get('third')]
        places += range(0, 3)

    result_tmpl = """
        with winners as (
            select 
            unnest(%s) player, 
            unnest(%s) place
        )
        select 
            p."_id" "id",
            p."Name" "name",
            p."Surname" "surname",
            p."Patronymic" "patronymic",
            p."Description" "description",
            p."Link" "link",
            p."Categories" "category"
        from winners
        join "Players" p on p."_id" = "player"
        order by p."Categories", "place"
    """

    cursor.execute(result_tmpl, [winners, places])
    result = cursor.fetchall()
    res_winners = []
    cat = result[0].get('category')
    players = []
    for val in result:
        if cat != val.get('category'):
            res_winners.append({'data': players, 'category': json.loads(cat)})
            cat = val.get('category')
            players = []
        players.append(val)
    else:
        res_winners.append({'data': players, 'category': json.loads(cat)})

    return res_winners


@connection_db
def get_third_timing(cursor, user_id, tournament_id):
    third_tmpl = """
        SELECT
            *
        FROM "EventTiming"
        WHERE 
            "third" IS TRUE
            AND "tournamentid" = %s
            AND "userid" = %s
        LIMIT 1
    """

    cursor.execute(third_tmpl, [user_id, tournament_id])
    third = cursor.fetchone()

    return [[{'name': third.get('fighterone')},
             {'name': third.get('fightertwo')}],
            [{'name': third.get('winner')}]]


