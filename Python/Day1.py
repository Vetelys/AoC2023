import regex as re
string_to_int = {"one":1, "two":2, "three":3, "four":4, "five":5, "six":6, "seven":7, "eight":8, "nine":9}

sum = 0
with open("./input1.txt", 'r') as file:
    for i, row in enumerate(file):
        matches = re.findall("\d", row)
        first = string_to_int.get(matches[0], matches[0])
        last = string_to_int.get(matches[-1], matches[-1])
        combined = int(str(first) + str(last))
        sum += combined
print(sum)
sum = 0
with open("./input1.txt", 'r') as file:
    for i, row in enumerate(file):
        matches = re.findall(f"\d|{'|'.join(string_to_int.keys())}", row, overlapped=True)
        first = string_to_int.get(matches[0], matches[0])
        last = string_to_int.get(matches[-1], matches[-1])
        combined = int(str(first) + str(last))
        sum += combined
print(sum)