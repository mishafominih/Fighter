from flask import Flask, request

from database.database import sign_in, registration, \
    create_tournament, tournament_list, add_new_player, \
    get_tournament_grid, get_players_for_tournament

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
def get_tournament_list():
    params = request.form
    result = tournament_list(**{'user_id': params.get('user')})
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
    result = get_tournament_grid(**params)
    if result:
        return result
    else:
        return {'result': False, 'message': 'Что-то пошло не так'}


app.run()
