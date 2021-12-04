#!/usr/bin/env python3

f = open("sample.txt","r")
sample = [ x.replace('\n','') for x in f.readlines()]

f = open("input.txt","r")
puzzle = [ x.replace('\n','') for x in f.readlines()]

class Board:
    def __init__(self):
        self.bingoBoard = []
        self.checked = []

    def board(self):
        return self.bingoBoard

    def addRow(self,line):
        cells = [ int(x) for x in line.split(' ') if len(x) > 0]
        self.bingoBoard.append(cells)

    def addChecked(self,number):
        self.checked.append(number)

    def bingo(self):
        for x in range(0,len(self.bingoBoard[0])):
            if self.checkRow(x) or self.checkColumn(x):
                unmarkedNumbers =  [item for sublist in self.bingoBoard for item in sublist if item not in self.checked]
                return (True,sum(unmarkedNumbers))
        return (False,0)

    def checkRow(self,r):
        num = len(self.bingoBoard[r])
        checked = len([ x for x in self.bingoBoard[r] if x in self.checked])
        return num == checked

    def checkColumn(self,c):
        num = len(self.bingoBoard[c]) #cheating
        checked = len([x[c] for x in self.bingoBoard if x[c] in self.checked])
        return num == checked

    def printBoard(self):
        def checked(x):
            if x in self.checked:
                return '*' + str(x) + '*'
            else:
                return x
        for x in self.bingoBoard:
            print ([checked(l) for l in x])

class Game:
    def __init__(self):
        self.boards = []
        self.tiles = []

    def parse(self,input):
        for line in input:
            if len(self.tiles) == 0:
                self.tiles = [ int(x) for x in line.split(',')]
            elif line == '':
                self.boards.append(Board())
            else:
                self.boards[-1].addRow(line)

    def printBoards(self):
        for x in self.boards:
            x.printBoard()
            print ('\n')

    def checkBingo(self):
        for x in self.boards:
            print (x.bingo())

    def play(self):
        for turn in self.tiles:
            for b in self.boards:
                b.addChecked(turn)
                (bingo,score) = b.bingo()
                if bingo:
                    return (score,turn)
        return (0,'')
        
    def calcResult(self,x):
        return x[0] * x[1]
        
# part 1
print ("Part 1")
s = Game()
s.parse(sample)
r = s.play()
print (s.calcResult(r))

p = Game()
p.parse(puzzle)
r = p.play()
print (p.calcResult(r))
