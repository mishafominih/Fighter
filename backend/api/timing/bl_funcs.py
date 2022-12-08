from database.database import get_tournament
from timing.base_timing import Timing
from timing.simple_timing import SimpleTiming


def get_timing(user_id, tournament_id) -> Timing:
    t = get_tournament(user_id, tournament_id)['type']
    return SimpleTiming(user_id, tournament_id)  # Заглушка, пока поддерживаем только один тип
