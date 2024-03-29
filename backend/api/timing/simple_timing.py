from timing.base_timing import Timing


class SimpleTiming(Timing):
    def __init__(self, user_id, tournament_id):
        super().__init__(user_id, tournament_id)
        self.index = 0

    def _generate_timing_for_category(self, players):
        result = []
        count = len(players)
        size = 2
        stage = 0
        while size < count:
            size *= 2
        for i in range(0, size // 2):
            second_i = i + size // 2
            item = self.add_timing_item(
                players[i].get("id"),
                players[second_i].get("id") if second_i < count else None,
                stage
            )
            result.append(item)
        if len(result) > 1:
            self._add_finish(result, True)

    def _add_finish(self, sub_result, add_third=False):
        def join(f, s, add_third=False):
            child = self.add_timing_item(f.winner, s.winner, f.stage+1, add_third)
            f.child = child
            s.child = child
            if add_third:
                f.secondchild = child
                s.secondchild = child
            return child

        count = len(sub_result)
        if count == 2:
            if add_third:
                join(sub_result[0], sub_result[1], add_third)
            return join(sub_result[0], sub_result[1])

        f = self._add_finish(sub_result[0: count // 2])
        s = self._add_finish(sub_result[count // 2: count])
        if add_third:
            join(f, s, add_third)
        return join(f, s)
