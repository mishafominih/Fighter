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
            array_agg(COALESCE(rec."one",'') || ',' || COALESCE(rec."two", '')) "Array"
        FROM
        (
            SELECT
                (SELECT "Name" FROM "Players" WHERE "_id" = "fighterone" LIMIT 1) "one",
                (SELECT "Name" FROM "Players" WHERE "_id" = "fightertwo" LIMIT 1) "two",
                "stage"
            FROM "EventTiming"
            WHERE "tournamentid" = %s
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

    return result


@connection_db
def add_new_timing(cursor, params):
    player_tmpl = """
            INSERT INTO public."EventTiming" 
            ("id", "userid","tournamentid","place","fighterone","fightertwo","winner","child","stage")
            VALUES  (%s, %s, %s, %s, %s, %s, %s, %s, %s)
            RETURNING "id";
        """

    for param in params:
        cursor.execute(player_tmpl, param)

    return cursor.fetchone()['id']


@connection_db
def write_winner(cursor, user_id, tournament_id, fight_id, winner_id):
    player_tmpl = """
            UPDATE public."EventTiming" 
            SET "winner" = %s
            WHERE 
                "userid" = %s
                AND "tournamentid" = %s
                AND "id" = %s
            RETURNING *
        """

    cursor.execute(player_tmpl, [winner_id, user_id, tournament_id, fight_id])
    return cursor.fetchone()


@connection_db
def write_player(cursor, user_id, tournament_id, fight_id, player_id):
    player_tmpl = """
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
                AND "id" = %s
            RETURNING *
        """

    cursor.execute(player_tmpl, [player_id, player_id, user_id, tournament_id, fight_id])
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
