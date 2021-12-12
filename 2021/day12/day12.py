#!/usr/bin/env python3

from functools import reduce

f = open("sample.txt","r")
sample = [ x.replace('\n','') for x in f.readlines()]

f = open("sample2.txt","r")
sample2 = [ x.replace('\n','') for x in f.readlines()]

f = open("input.txt","r")
puzzle = [ x.replace('\n','') for x in f.readlines()]

# part 1
class CaveMap:
    def __init__(self, input):
        self.map = {}
        self.paths = []
        self.parse(input)

    def parse(self,input):
        map = {}
        for a in input:
            (from_cave,to_cave) = a.split('-')
            if from_cave in map:
                map[from_cave].append(to_cave)
            else:
                map[from_cave] = [to_cave]
            if to_cave in map:
                map[to_cave].append(from_cave)
            else:
                map[to_cave] = [from_cave]
        self.map = map

    def get_paths(self):
        return self.paths

    def find_paths(self, start, visit, path = []):
        path.append(start)
        if start in self.map:
            for p in self.map[start]:
                if visit(p,path) or p.upper() == p:
                    if p == 'end':
                        n = path.copy()
                        n.append(p)
                        self.paths.append(n)
                    else:
                        self.find_paths(p, visit, path.copy())

    def part1(self):
        self.find_paths('start', lambda x,y: x not in y)
        return len(self.get_paths())

    def part2(self):
        def check(cave,path):
            counts = {}
            for c in [x for x in path if x.lower() == x]:
                counts[c] = 1 if c not in counts else counts[c] + 1
            doubles = len([x for x in counts if counts[x] == 2])
            return cave not in counts or (counts[cave] == 1 and doubles == 0)
        self.find_paths('start', check )
        return len(self.get_paths())

print ('Part 1')
sample_map = CaveMap(sample)
print (sample_map.part1())

sample_map = CaveMap(sample2)
print (sample_map.part1())

puzzle_map = CaveMap(puzzle)
print (puzzle_map.part1())

print ('Part 2')
sample_map = CaveMap(sample)
print (sample_map.part2())

sample_map = CaveMap(sample2)
print (sample_map.part2())

puzzle_map = CaveMap(puzzle)
print (puzzle_map.part2())
