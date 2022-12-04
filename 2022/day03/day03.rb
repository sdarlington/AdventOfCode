#!/usr/bin/env ruby

require 'set'

def day3(f)
  game = []
  File.foreach(f) { |each_line|
    mid = each_line.length / 2
    game.append( [ each_line[0...mid], each_line[mid..] ] )
  }
  
  return game
end

def score(x)
  if x.match?("[a-z]")
	x.ord - 'a'.ord + 1
  elsif x.match?("[A-Z]")
	x.ord - 'A'.ord + 27
  else
	0
  end
end

def part1(f)
  game = day3(f)
  
  scores = game.map do |g|
    compartment1 = Set.new(g.first.split(''))
    compartment2 = Set.new(g.last.split(''))
    
    both = compartment1.intersection(compartment2)
    
    score = both.map { |x| score(x) }
    score.sum
  end
  puts scores.sum
end

def part2(f)
  game = day3(f).map { |x| "#{x.first}#{x.last}".chomp }
  
  result = []
  while game.size > 0 do
    group = game.shift(3).map { |x| Set.new(x.split('')) } 
    xx = group.reduce(:intersection)
    result.append(xx.first)
  end
  
  score = result.map { |x| score(x) }
  puts score.sum
end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part2("sample.txt")
part2("input.txt")