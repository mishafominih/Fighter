
import json

from aiohttp import web
from aiohttp_session import get_session, setup, SimpleCookieStorage


def start_server():
    routes = web.RouteTableDef()

    @routes.post('/api/login')
    async def log_in(request):
        return web.Response(status=200, text=json.dumps({'user_id': 10, 'status': 10}))

    app = web.Application()
    setup(app, SimpleCookieStorage())
    app.add_routes(routes)
    web.run_app(app)


start_server()
