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
        SELECT "Password" = %s
        FROM "Users"
        WHERE "Login" = %s
        LIMIT 1;"""
    cursor.execute(tmpl, [password, login])
    record = cursor.fetchone()
    if not record or False in record:
        return 'Неверный логин или пароль'
    return True is record['Password']


@connection_db
def registration(cursor, password, login):
    cursor.execute("""SELECT "Login" FROM "Users" WHERE "Login" = %s;""", [sql.Literal(login)])
    result = cursor.fetchone()
    if result:
        return 'Пользователь с таким логином уже есть'
    tmpl = """INSERT INTO public."Users" ("Login", "Password") VALUES (%s, %s);"""
    cursor.execute(tmpl, [sql.Literal(login), sql.Literal(password)])


@connection_db
def create_tournament(cursor, **params):
    event_tmpl = """
        INSERT INTO public."Event" ("Name", "Description", "Type", "CountThread", "User") VALUES  (%s, %s, %s, %s, %s)
        RETURNING "_id";
    """
    cursor.execute(event_tmpl, [params.get('name'), params.get('description'),
                                params.get('count_thread'), params.get('type'), params.get('user_id')])
    event_id = cursor.fetchone()

    categories_tmpl = """
        INSERT INTO public."Specifications" ("Event", "Name", "Range") VALUES (%s, %s, %s);
    """
    categories = params.get('categories')
    data = []
    for val in categories:
        data += [event_id, val.get('name'), json.dumps(val.get('range'))]

    cursor.execute(categories_tmpl * len(categories), data)


@connection_db
def tournament_list(cursor, user_id):
    tmpl = """select * from "Event" where "User" = %s"""
    cursor.execute(tmpl, [user_id])
    data = cursor.fetchall()
    return data
