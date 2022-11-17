import psycopg2
import json

from psycopg2 import sql


def connection_db(func):
    def connect(**args):
        with open('config.json', 'r') as config:
            db_data = json.loads(config.read()) or {}
        conn = psycopg2.connect(f"""
            host={db_data.get('host')}
            port={db_data.get('port')}
            dbname={db_data.get('dbname')}
            user={db_data.get('user')}
            password={db_data.get('password')}
            target_session_attrs=read-write
        """)
        cursor = conn.cursor()
        result = func(cursor, **args)
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
        LIMIT 1"""
    cursor.execute(tmpl, [password, login])
    record = cursor.fetchone()
    if not record or False in record:
        return 'Неверный логин или пароль'
    return True in record


@connection_db
def registration(cursor, password, login):
    cursor.execute("""SELECT "Login" FROM "Users" WHERE "Login" = %s """, [sql.Literal(login)])
    result = cursor.fetchone()
    if result:
        return "Пользователь с таким логином уже есть"
    tmpl = """INSERT INTO public."Users" ("Login", "Password") VALUES (%s, %s);"""
    cursor.execute(tmpl, [sql.Literal(login), sql.Literal(password)])


@connection_db
def sign_i_n(cursor):
    cursor.execute('SELECT * FROM "Users"')

    print(cursor.fetchone())


sign_i_n()

