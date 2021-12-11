#!/usr/bin/env python3

f = open("sample.txt","r")
sample = [ [int(a) for a in x.replace('\n','')] for x in f.readlines()]

f = open("input.txt","r")
puzzle = [ [int(a) for a in x.replace('\n','')] for x in f.readlines()]

# part 1
class OctoGrid:
    def __init__(self,input):
        self.octo = input

    def print(self):
        for y in range(0,self.maxY()):
            for x in range(0,self.maxX()):
                print (self.energy_level(x,y), end='')
            print ()

    def maxX(self):
        return 10
    
    def maxY(self):
        return 10

    def energy_level(self,x,y):
        return self.octo[y][x]
    
    def set_energy_level(self,x,y,v):
        self.octo[y][x] = v

    def adjacent_coords(self,x,y):
        adj = [
            (x-1,y-1), (x,y-1), (x+1,y-1),
            (x-1,y  ),          (x+1,y  ),
            (x-1,y+1), (x,y+1), (x+1,y+1)
            ]
        return [(x,y) for (x,y) in adj if x >= 0 and x < self.maxX() and y >= 0 and y < self.maxY()]
        
    def next_state(self):
        self.octo = [ [ y+1 for y in x]  for x in self.octo]
        flashes = []
        # print (self.octo)
        while len([item for sublist in self.octo for item in sublist if item > 9]) != len(flashes):
            # print ('flashes: ', len([item for sublist in self.octo for item in sublist if item > 9]))
            # print ('count: ', len(flashes))
            for x in range(0,self.maxX()):
                for y in range(0,self.maxY()):
                    if self.energy_level(x,y) > 9 and (x,y) not in flashes:
                        # self.set_energy_level(x,y,0)
                        flashes.append((x,y))
                        for (x1,y1) in self.adjacent_coords(x,y):
                            self.set_energy_level(x1,y1,self.energy_level(x1,y1) + 1)
        for x in range(0,self.maxX()):
            for y in range(0,self.maxY()):
                if self.energy_level(x,y) > 9:
                    self.set_energy_level(x,y,0)
        return len(flashes)

    def part1(self, count=100):
        flashes = 0
        for step in range(0,count):
            flashes = flashes + self.next_state()
        return flashes

print('Part 1')
octo_sample = OctoGrid(sample)
print (octo_sample.part1())

octo_puzzle = OctoGrid(puzzle)
print (octo_puzzle.part1())
