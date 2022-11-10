from flask import Flask

# from database import sign_in, registration

logins = {'my@my': '1234'}
keys = ['1234']
tournaments = {'1234': [
    {'time': '12:30', 'place': 'Зал 1', 'fighter_one': 'Фоминых М.И', 'fighter_two': 'Андреев А.А.', 'score': '3:4'},
    {'time': '13:30', 'place': 'Зал 2', 'fighter_one': 'Фоминых М.И', 'fighter_two': 'Путров Е.В.', 'score': 'Сейчас идет'},
    {'time': '14:30', 'place': 'Зал 3', 'fighter_one': 'Фоминых М.И', 'fighter_two': 'Андреев А.А.', 'score': None}
]}

app = Flask(__name__)


@app.route('/api/login', methods=['GET', 'POST'])
def check_login(params):
    email = params.get('login')
    password = params.get('password')
    if logins.get(email) == password:
        return {'result': True}
    else:
        return {'result': False, 'message': 'Неверный логин или пароль'}
    # result = sign_in(**params)
    # if isinstance(result, str):
    #     return {'result': False, 'message': result}
    # return {'result': result}


@app.route('/api/registration', methods=['GET', 'POST'])
def reg(params):
    email = params.get('login')
    password = params.get('password')
    if not logins.get(email):
        logins[email] = password
        return {'result': True}
    else:
        return {'result': False, 'message': 'Пользователь с таким логином уже зарегистрирован'}
    # result = registration(**params)
    # if result:
    #     return {'result': False, 'message': result}
    # return {'result': not result}


@app.route('/api/join_to_tournament', methods=['GET', 'POST'])
def join_to_tournament(params):
    key = params.get('key')
    return {'result': tournaments.get(key)}


@app.route('/api/tournament_list', methods=['GET', 'POST'])
def get_tournament_list(params):
    key = params.get('key')
    return {'result': key in keys}


app.run()
