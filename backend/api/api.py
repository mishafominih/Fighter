from flask import Flask, request

from database.database import sign_in, registration, create_tournament, tournament_list

logins = {'my@my': '1234'}
keys = ['1234']
tournaments = [
    {'time': '12:30', 'place': 'Зал 1', 'fighter_one': 'Фоминых М.И.', 'fighter_two': 'Андреев А.А.', 'score': '3:4'},
    {'time': '12:30', 'place': 'Зал 2', 'fighter_one': 'Иванов Ю.И.', 'fighter_two': 'Салимов А.Д.', 'score': '3:2'},
    {'time': '13:00', 'place': 'Зал 1', 'fighter_one': 'Ибрагимов А.В.', 'fighter_two': 'Юнусов А.А.', 'score': '3:1'},
    {'time': '13:00', 'place': 'Зал 2', 'fighter_one': 'Петров И.И.', 'fighter_two': 'Сидоров А.М.', 'score': '4:5'},
    {'time': '13:30', 'place': 'Зал 1', 'fighter_one': 'Лебедев А.Л.', 'fighter_two': 'Ларинов Ю.А.', 'score': '1:2'},
    {'time': '13:30', 'place': 'Зал 2', 'fighter_one': 'Соломатин М.А.', 'fighter_two': 'Кузьминых А.И.', 'score': '2:4'},
    {'time': '14:00', 'place': 'Зал 1', 'fighter_one': 'Новиков А.В.', 'fighter_two': 'Ласточкин А.А.', 'score': '4:5'},
    {'time': '14:00', 'place': 'Зал 2', 'fighter_one': 'Макшанцев М.И.', 'fighter_two': 'Альмухаметов А.И.', 'score': '4:1'},
    {'time': '14:30', 'place': 'Зал 1', 'fighter_one': 'Андреев А.А.', 'fighter_two': 'Иванов Ю.И.',
     'score': 'Сейчас идет'},
    {'time': '14:30', 'place': 'Зал 2', 'fighter_one': 'Ибрагимов А.В.', 'fighter_two': 'Сидоров А.М.',
     'score': 'Сейчас идет'},
    {'time': '15:00', 'place': 'Зал 1', 'fighter_one': 'Ларинов Ю.А.', 'fighter_two': 'Кузьминых А.И.',
     'score': ''},
    {'time': '15:00', 'place': 'Зал 2', 'fighter_one': 'Ласточкин А.А.', 'fighter_two': 'Макшанцев М.И.',
     'score': ''}
]

app = Flask(__name__)


@app.route('/api/login', methods=['GET', 'POST'])
def check_login():
    params = request.form
    result = sign_in(**params)
    if isinstance(result, str):
        return {'result': False, 'message': result}
    return {'result': result}


@app.route('/api/registration', methods=['GET', 'POST'])
def reg():
    params = request.form
    result = registration(**params)
    if result:
        return {'result': False, 'message': result}
    return {'result': not result}


@app.route('/api/join_to_tournament', methods=['GET', 'POST'])
def join_to_tournament():
    params = request.form
    key = params.get('key')
    return {'result': True}


@app.route('/api/tournament_list', methods=['GET', 'POST'])
def get_tournament_list():
    params = request.form
    result = tournament_list(**{'user_id': params.get('key')})
    return result


@app.route('/api/create_tournament', methods=['GET', 'POST'])
def tournament():
    params = request.form
    event_id = create_tournament(**params)
    return {'result': True, 'id': event_id}


app.run()
