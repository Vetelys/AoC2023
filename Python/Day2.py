from functools import reduce

available_cubes = {"red": 12, "green": 13, "blue": 14}

sum_ids = 0

with open("input2.txt", 'r') as file:
    for row in file:
        game, info = row.split(":")
        id = int(game[5:])
        valid = True
        for set in info.split(";"):
            for nr_balls in set.split(","):
                amount, color = nr_balls.strip().split(" ")
                if available_cubes[color] < int(amount):
                    valid = False
        if (valid):
            sum_ids += id
    print(sum_ids)


sum_of_power = 0
with open("input2.txt", 'r') as file:
    for row in file:
        game, info = row.split(":")
        id = int(game[5:])
        valid = True
        minimums = {"red" : 0, "green" : 0, "blue" : 0}
        for set in info.split(";"):
            for nr_balls in set.split(","):
                amount, color = nr_balls.strip().split(" ")
                minimums[color] = max(minimums[color], int(amount))
        print(minimums)
        sum_of_power += reduce(lambda x, y: x*y, minimums.values())
    print(sum_of_power)
