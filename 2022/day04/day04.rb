#!/usr/bin/env ruby

require 'set'

def day4(f)
  File.read(f)
      .split
      .map { |l| l.split(',').map { |x| x.split('-').map { |y| y.to_i } } }
end

def contained?(x,y)
  s1 = Set.new((x.first..x.last))
  s2 = Set.new((y.first..y.last))
  
  s1 <= s2 or s2 <= s1
end

def part1(f)
  game = day4(f)
  puts game.map { |x| contained?(x.first,x.last) }
      .filter { |x| x }
      .count
end

def contained2?(x,y)
  Set.new((x.first..x.last)).intersect?(Set.new((y.first..y.last)))
end

def part2(f)
  game = day4(f)
  puts game.map { |x| contained2?(x.first,x.last) }
      .filter { |x| x }
      .count
end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part2("sample.txt")
part2("input.txt")