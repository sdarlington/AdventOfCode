#!/usr/bin/env ruby

def day2(f)
  game = []
  File.foreach(f) { |each_line|
    each_line.match(/([ABC]) ([XYZ])/) { |m| game.append( [ *m.captures ] ) }
  }
  
  return game
end


def part1(f)
  game = day2(f)
  
  score = { "A":1, "B":2, "C":3, "X":1, "Y":2, "Z":3 }
  
  result = game.map do |g|
    me = score[g.last.to_sym]
    you = score[g.first.to_sym]
    
    turn = 0
    if me == you
      turn = 3
    elsif me == 1 and you == 3
      turn = 6
    elsif me == 2 and you == 1
      turn = 6
    elsif me == 3 and you == 2
      turn = 6
    end
    
    score[g.last.to_sym] + turn
  end
  
  puts result.sum
end

def part2(f)
  game = day2(f)
  
  score = { "A":1, "B":2, "C":3, "X":1, "Y":2, "Z":3 }
  lose = { "A":3, "B":1, "C":2 }
  win = { "A":2, "B":3, "C":1 }
  
  result = game.map do |g|
    me = score[g.last.to_sym]
    you = score[g.first.to_sym]
    turn = 0
    if me == 2 # draw
      turn = 3 + you
    elsif me == 1 # lose
      turn = 0 + lose[g.first.to_sym]
    elsif me == 3 # win
      turn = 6 + win[g.first.to_sym]
    end
    
    turn
  end
  
  puts result.sum
end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part2("sample.txt")
part2("input.txt")