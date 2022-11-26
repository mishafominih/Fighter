from flask import Flask, request

from database.database import sign_in, registration, \
    create_tournament, get_tournaments_for_db, add_new_player, \
    get_tournament_list, get_players_for_tournament
from timing.simple_timing import SimpleTiming

app = Flask(__name__)


@app.route('/api/login', methods=['GET', 'POST'])
def check_login():
    params = request.form
    result = sign_in(**params)
    if isinstance(result, str):
        return {'result': False, 'message': result}
    return result


@app.route('/api/registration', methods=['GET', 'POST'])
def reg():
    params = request.form
    result = registration(**params)
    if isinstance(result, str):
        return {'result': False, 'message': result}
    return {'result': bool(result), 'id': result}


@app.route('/api/join_to_tournament', methods=['GET', 'POST'])
def join_to_tournament():
    params = request.form
    key = params.get('key')
    return {'result': True}


@app.route('/api/get_tournaments', methods=['GET', 'POST'])
def get_tournaments():
    params = request.form
    result = get_tournaments_for_db(**{'user_id': params.get('user')})
    return result


@app.route('/api/create_tournament', methods=['GET', 'POST'])
def tournament():
    params = request.form
    event_id = create_tournament(**params)
    if event_id:
        return {'result': True, 'id': event_id}
    else:
        return {'result': False, 'message': 'Мы не смогли создать событие'}


@app.route('/api/add_player', methods=['GET', 'POST'])
def add_player():
    params = request.form
    player_id = add_new_player(**params)
    if player_id:
        return {'result': True, 'id': player_id}
    else:
        return {'result': False, 'message': 'Что-то пошло не так'}


@app.route('/api/get_players', methods=['GET', 'POST'])
def get_players():
    params = request.form
    result = get_players_for_tournament(**{'user_id': params.get('user'), 'tournament_id': params.get('tournament_id')})
    return result


@app.route('/api/tournament_grid', methods=['GET', 'POST'])
def tournament_grid():
    params = request.form
    result = get_tournament_list(**params)
    if result:
        return result
    else:
        return {'result': False, 'message': 'Что-то пошло не так'}


@app.route('/api/tournament_list', methods=['GET', 'POST'])
def tournament_list():
    params = request.form
    result = get_tournament_list(**params)
    players = get_players_for_tournament(**params)

    def find(id):
        for rec in players:
            if rec.get('id') == id:
                return rec
        return None

    for rec in result:
        rec['fighter_one'] = find(rec.get('fighter_one'))
        rec['fighter_two'] = find(rec.get('fighter_two'))

    if result:
        return [rec for rec in result if rec.get('fighter_one') and rec.get('fighter_two')]
    else:
        return {'result': False, 'message': 'Что-то пошло не так'}


@app.route('/api/start_tournament', methods=['GET', 'POST'])
def start_tournament():
    params = request.form
    user_id, tournament_id = params.get('user'), params.get('tournament_id')
    # Добавить проверку на уже существующее расписание
    timing = SimpleTiming(user_id, tournament_id)  # Выбираем нужную логику генерации расписания
    timing.generate_timing()  # Генерируем распределение и записываем в бд
    pass


app.run()
