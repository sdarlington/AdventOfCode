#!/usr/bin/env ruby

require 'ostruct'

def day5(f)
  record_moves = false
  stacks = []
  moves = []
  File.foreach(f) { |each_line|
    if each_line.match?(/^$/)
      record_moves = true
    elsif each_line.match(/[0-9]+ +[0-9]+/)
      record_moves = true
    elsif not record_moves
      stacks.append(each_line.scan(/(   |\[[A-Z]\]) ?/).flat_map(&:compact))
    else
      each_line.match(/move ([0-9]*) from ([0-9]*) to ([0-9]*)/) do |m|
        moves.append( OpenStruct.new(count: m.captures[0].to_i, from: m.captures[1].to_i, to: m.captures[2].to_i) )
      end
    end
  }
  
  stacks.reverse!

  output = stacks.shift.map { |x| [ x ] }
  stacks.each do |r|
    output.zip(r).each do |x|
      if x.last.match(/^\[/)
        x.first.append(x.last)
      end
    end
  end
  
  return OpenStruct.new(stacks: output, moves: moves)
end

def print_game(game)
    num = (1..game.stacks.size)
    num.zip(game.stacks).each { |x| puts "#{x.first} #{x.last.join.gsub(/[\[\]]/, '')}" }
end

def part1(f)
  game = day5(f)
  
  game.moves.each do |move|
    crate = game.stacks[move.from - 1].pop(move.count)
    crate.reverse.each { |c| game.stacks[move.to - 1].push(c) }
  end
  
  puts game.stacks.map { |x| x.last }.join().gsub(/[\[\]]/, '')
end

def part2(f)
  game = day5(f)
  
  game.moves.each do |move|
    crate = game.stacks[move.from - 1].pop(move.count)
    crate.each { |c| game.stacks[move.to - 1].push(c) }
  end
  
  puts game.stacks.map { |x| x.last }.join().gsub(/[\[\]]/, '')
end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part2("sample.txt")
part2("input.txt")