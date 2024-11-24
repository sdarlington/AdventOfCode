#!/usr/bin/env ruby

require 'set'
require 'ostruct'

def day9(f)
  File.readlines(f).map do |x|
    el = x.chomp.split(' ')
    OpenStruct.new(direction: el.first, distance: el.last.to_i)
  end
end

def print_grid(rope, dx = 10, dy = 10)
  coords = {}
  rope.each_with_index do |r,i|
    if not coords.include?(r)
      coords[r] = i
    end
  end
    
# 	puts size
# puts "****"
# puts coords
puts "*****"
	for y in dy.downto(0)
	  for x in 0..dx
		cell = OpenStruct.new(x:x, y:y)
		if coords.include?(cell)
		  print coords[cell]
		else
		  print "."
		end
	  end
	  puts
	end
  
end

def part1(f, c = 2)
  game = day9(f)
  
  visited = Set.new()
    
  rope = c.times.map { OpenStruct.new(x: 0, y: 0) }
  
  visited << rope[0].dup

  game.each do |turn|
#     puts turn
    if turn.direction == 'L'
      move = lambda { |x| x.x = x.x - 1 }
    elsif turn.direction == 'R'
      move = lambda { |x| x.x = x.x + 1 }
    elsif turn.direction == 'U'
      move = lambda { |x| x.y = x.y + 1 }
    elsif turn.direction == 'D'
      move = lambda { |x| x.y = x.y - 1 }
    end
    
    turn.distance.times do
#       puts "Next"
#       rope.each_with_index do |r,i|
      for i in 0.upto(c-1) do
          if i == 0
          	move.call(rope[0])
          else
	  
	# 	      puts "#{rope[i-1]} #{rope[i]}"
		  
			  dx = rope[i-1].x - rope[i].x
			  dy = rope[i-1].y - rope[i].y
			  if    dy == 0 and dx.abs == 2
	# 		  puts "A #{ i }"
				rope[i].x = rope[i].x + dx / 2
			  elsif dx == 0 and dy.abs == 2
	# 		  puts "B #{ i }"
				rope[i].y = rope[i].y + dy / 2
			  elsif dx.abs >= 1 and dy.abs == 2
	# 		  puts "C #{ i }"
				rope[i].x = rope[i-1].x
				rope[i].y = rope[i].y + (dy <=> 0)
			  elsif dx.abs == 2 and dy.abs >= 1
	# 		  puts "D #{ i }"
				rope[i].x = rope[i].x + (dx <=> 0)
				rope[i].y = rope[i-1].y
			  else
	# 		    puts "E #{ i } #{ dx } #{ dy }"
			  end
		  end
# 		  r = rope[i]
      end
#       puts "*"
#       puts rope
      visited << rope.last.dup
print_grid(rope)
    end
  end
  puts visited.size
end

def part2(f)
  part1(f, 10)
end

# puts "Part 1"
# part1("sample.txt")
# part1("input.txt")

puts "Part 2"
part2("sample.txt")
# not 2265
# part2("input.txt")