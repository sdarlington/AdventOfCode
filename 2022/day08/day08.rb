#!/usr/bin/env ruby

require 'set'

def day8(f)
  File.readlines(f).map { |x| x.chomp.split('').map { |x| x.to_i } }
end


def getTree(map,x,y)
  map[y][x]
end

def getMaxX(map)
  map[0].size
end

def getMaxY(map)
  map.size
end

def score1(game, x, y, xs, ys)
  visible = Set.new()

  height = -1
  while true do    
    break if x < 0 or x == getMaxX(game) or y < 0 or y == getMaxY(game)
    
    tree = getTree(game,x,y)
    if tree > height
      visible << [x,y]
      height = tree
    end
    
    x = xs.call(x)
    y = ys.call(y)

  end
  
  visible
end


def part1(f)
  game = day8(f)
    
  maxX = getMaxX(game) - 1
  maxY = getMaxY(game) - 1
  
  x1 = (0...getMaxY(game)).map { |y| score1(game, 0, y, lambda { |x| x + 1 }, lambda { |y| y }) }
                          .reduce (Set.new) { |a,b| a.union(b) }
  x2 = (0...getMaxY(game)).map { |y| score1(game, maxX, y, lambda { |x| x - 1 }, lambda { |y| y }) }
                          .reduce (Set.new) { |a,b| a.union(b) }
  x3 = (0...getMaxX(game)).map { |x| score1(game, x, 0, lambda { |x| x }, lambda { |y| y + 1 }) }
                          .reduce (Set.new) { |a,b| a.union(b) }
  x4 = (0...getMaxX(game)).map { |x| score1(game, x, maxY, lambda { |x| x }, lambda { |y| y - 1 }) }
                          .reduce (Set.new) { |a,b| a.union(b) }
  all = x1 | x2 | x3 | x4
  puts all.size  
end

def score(grid, x, y, xs, ys)
  currX = x
  currY = y
  maxX = getMaxX(grid)
  maxY = getMaxY(grid)
  
  height = getTree(grid, currX, currY)
  c = 0
  while true do;
    currX = xs.call(currX)
    currY = ys.call(currY)
    
    break if currX < 0 or currX == maxX or currY < 0 or currY == maxY
    
    newHeight = getTree(grid, currX, currY)
    c = c + 1
    if newHeight >= height
      break
    end
  end
  c
end

def scoreTree(grid, x, y)
  score(grid, x, y, lambda { |x| x     }, lambda { |y| y - 1 }) *
  score(grid, x, y, lambda { |x| x - 1 }, lambda { |y| y     }) *
  score(grid, x, y, lambda { |x| x     }, lambda { |y| y + 1 }) *
  score(grid, x, y, lambda { |x| x + 1 }, lambda { |y| y     })
end

def part2(f)
  grid = day8(f)
            
  puts scoreTree(grid, 2,1)
  puts scoreTree(grid, 2,3)

  best_score = 0
  for x in 0...getMaxX(grid) do
    for y in 0...getMaxY(grid) do
      s = scoreTree(grid,x,y)
      if s > best_score
        best_score = s
      end
    end
  end
  puts best_score

end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part2("sample.txt")
part2("input.txt")