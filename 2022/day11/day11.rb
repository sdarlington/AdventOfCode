#!/usr/bin/env ruby

require 'set'
require 'ostruct'

def day11(f)
  monkeys = []
  current_monkey = nil
  items = nil
  operation = nil
  test_op = nil
  test_true = nil
  test_false = nil
  File.readlines(f).map do |x|
    if x.match?(/^Monkey/)
      current_monkey = x.match(/^Monkey (\d+):/)[1]
    elsif x.match?(/^\s+Starting items:/)
      items = x.scan(/\d+/).map { |x| x.to_i }
    elsif x.match?(/^\s+Operation:/)
      op_text = x.match(/^\s+Operation: new = (.*)/)[1]
      operation = lambda { |old| eval op_text }
    elsif x.match?(/^\s+Test:/)
      op = x.match(/divisible by (\d+)/)
      if op == nil
        puts "Not divisible!"
      else
        test_op = lambda { |x| x % op[1].to_i == 0}
      end
    elsif x.match?(/^\s+If true/)
      op = x.match(/throw to monkey (\d+)/)
      if op == nil
        puts "No monkey!"
      else
        test_true = op[1].to_i
      end
    elsif x.match?(/^\s+If false/)
      op = x.match(/throw to monkey (\d+)/)
      if op == nil
        puts "No monkey!"
      else
        test_false = op[1].to_i
      end
    elsif x.match?(/^\s*$/)
      monkeys.append(OpenStruct.new(monkey: current_monkey, items: items,
                                    operation: operation, test_op: test_op,
                                    test_true: test_true, test_false: test_false,
                                    inspected: 0))
    end
  end
  monkeys.append(OpenStruct.new(monkey: current_monkey, items: items,
                                operation: operation, test_op: test_op,
                                test_true: test_true, test_false: test_false,
                                inspected: 0))
#   puts monkeys
  monkeys
end


def part1(f, rounds = 20, worry_divisor = 3)
  game = day11(f)
  
  rounds.times do
    game.each do |m|
      m.inspected = m.inspected + m.items.count
      m.items.each do |i|
        op_result = m.operation.call(i)
        ni = op_result / worry_divisor
        if m.test_op.call(ni)
          throw_to = m.test_true
        else
          throw_to = m.test_false
        end
        game[throw_to].items.push(ni)
      end
      m.items.clear
    end
  end
  puts game.map { |x| x.inspected }.sort.reverse.take(2).reduce { |a,b| a * b }
end

def part2(f)
  part1(f, 1000, 1)
end

puts "Part 1"
part1("sample.txt")
part1("input.txt")

puts "Part 2"
part2("sample.txt")
# part2("input.txt")