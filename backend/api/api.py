from flask import Flask, request

from database.database import sign_in, registration, \
    create_tournament, get_tournaments_for_db, add_new_player, \
    get_tournament_list, get_players_for_tournament, get_tournament_result, \
    write_status, get_tournament_grid, get_third_timing
from timing.bl_funcs import get_timing
from timing.simple_timing import SimpleTiming

app = Flask(__name__)


def check_exeption(func):
    def res(*args, **kwards):
        try:
            return func(*args, **kwards)
        except Exception as e:
            print(f'При обработке запроса произошло исключение: {str(e)}')
            return {'result': False, 'message': str(e)}

    return res


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
    result = get_tournaments_for_db(params.get('user_id'))
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
    result = get_players_for_tournament(**{'tournament_id': params.get('tournament_id')})
    return result


@app.route('/api/tournament_grid', methods=['GET', 'POST'])
def tournament_grid():
    params = request.form
    result = get_tournament_grid(**params)
    if result:
        return result
    else:
        return {'result': False, 'message': 'Что-то пошло не так'}


@app.route('/api/tournament_list', methods=['GET', 'POST'])
def tournament_list():
    params = request.form
    tournament_id = params.get('tournament_id')
    result = get_tournament_list(tournament_id)
    players = get_players_for_tournament(tournament_id)

    def find(id):
        for rec in players:
            if rec.get('id') == id:
                return rec
        return None

    for rec in result:
        rec['fighter_one'] = find(rec.get('fighter_one'))
        rec['fighter_two'] = find(rec.get('fighter_two'))
        if rec.get('winner'):
            rec['winner'] = find(rec.get('winner'))

    if result:
        return [rec for rec in result if rec.get('fighter_one') and rec.get('fighter_two')]
    else:
        return {'result': False, 'message': 'Что-то пошло не так'}


@app.route('/api/start_tournament', methods=['GET', 'POST'])
def start_tournament():
    params = request.form
    user_id, tournament_id = params.get('user_id'), params.get('tournament_id')
    # Добавить проверку на уже существующее расписание
    timing = get_timing(user_id, tournament_id)
    timing.generate_timing()  # Генерируем распределение и записываем в бд
    write_status(user_id, tournament_id, 1)
    return {'result': True, 'message': ""}


@app.route('/api/set_winner', methods=['GET', 'POST'])
@check_exeption
def set_winner():
    params = request.form
    user_id, tournament_id = params.get('user_id'), params.get('tournament_id')
    fight_id, winner_id = params.get('fight_id'), params.get('winner_id')
    one_score, two_score = params.get('one_score'), params.get('two_score')
    timing = get_timing(user_id, tournament_id)
    timing.set_result(fight_id, winner_id, one_score, two_score)
    return {'result': True, 'message': ""}


@app.route('/api/get_result', methods=['GET', 'POST'])
def tournament_result():
    params = request.form
    user_id, tournament_id = params.get('user_id'), params.get('tournament_id')
    return get_tournament_result(user_id, tournament_id)


@app.route('/api/get_third', methods=['GET', 'POST'])
def get_third():
    params = request.form
    user_id, tournament_id = params.get('user_id'), params.get('tournament_id')
    return get_third_timing(user_id, tournament_id)


app.run()

