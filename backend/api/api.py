from flask import Flask, request

from database.database import sign_in, registration, create_tournament, tournament_list

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


app.run()
