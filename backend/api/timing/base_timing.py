import json

from database.database import get_players_for_tournament, add_new_timing


class Timing:
    def __init__(self, user_id, tournament_id):
        self.user_id = user_id
        self.tournament_id = tournament_id
        self.result = []
        self.id = 0
        pass

    def generate_timing(self):
        # Получим список всех участников
        players = get_players_for_tournament(**{'user_id': self.user_id, 'tournament_id': self.tournament_id})
        data = {}  # Распределение участников по категориям
        for player in players:  # Обработаем полученных участников в более удобный вид
            cat = player.get('categories')
            if cat:
                j = json.loads(cat)
                cat = (j.get('name'), j.get('value'))
            if cat in data.keys():
                data[cat].append(player)
            else:
                data[cat] = [player]
        for cat, players in data.items():  # Теперь распределим участников между собой в категориях
            self._generate_timing_for_category(players)
        self._write_to_bd()

    def set_result(self):
        pass

    def change(self):
        pass

    def add_timing_item(self, player_one, player_two):
        item = self.TimingItem(self.id, player_one, player_two)
        self.result.append(item)
        self.id += 1
        return item

    def _generate_timing_for_category(self, players):
        pass

    def _write_to_bd(self):
        data = [item.get_data_for_db(self.user_id, self.tournament_id) for item in self.result]
        add_new_timing(**{"params": data})

    def _iterate_by(self, data, func):
        item = None
        for i in data:
            if item:
                func(item, i)
                item = None
            else:
                item = i

    class TimingItem:
        def __init__(self, id, player_one, player_two):
            self.id = id
            self.player_one = player_one
            self.player_two = player_two
            self.winner = player_one if player_one and not player_two else None
            self.child = None
            self.place = ""

        def get_data_for_db(self, user_id, tournament_id):
            return [
                self.id,
                user_id,
                tournament_id,
                self.place,
                self.player_one,
                self.player_two,
                self.winner,
                self.child.id if self.child else None
            ]