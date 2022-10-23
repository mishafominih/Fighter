from flask import Flask
from database import sign_in, registration


app = Flask(__name__)


@app.route('/api/login')
def check_login(params):
    result = sign_in(**params)
    if isinstance(result, str):
        return {'result': False, 'message': result}
    return {'result': result}


@app.route('/api/registration')
def reg(params):
    result = registration(**params)
    if result:
        return {'result': False, 'message': result}
    return {'result': not result}
