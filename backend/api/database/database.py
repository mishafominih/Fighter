import psycopg2
import json

from psycopg2 import sql, extras


def connection_db(func):
    def connect(**args):
        conn = psycopg2.connect(f"""
            host=rc1b-e1m47fv71pchwvru.mdb.yandexcloud.net
            port=6432
            dbname=fighter
            user=yukey
            password=postgres
            target_session_attrs=read-write
        """)
        cursor = conn.cursor(cursor_factory=extras.RealDictCursor)
        result = func(cursor, **args)

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
def tournament_list(cursor, user_id):
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
def get_tournament_grid(**params):
    pass

