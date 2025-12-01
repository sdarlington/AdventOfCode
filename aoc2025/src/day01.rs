

#[derive(Debug)]
enum Direction { L , R }

#[derive(Debug)]
struct SafeMove {
  direction : Direction,
  clicks : i32
}

fn parse_input(filename : &str) -> Vec<SafeMove> {
    let cols = crate::helper::parse_input(filename, |line| {
        let (direction,clicks) = line.split_at(1);
        let clicks_i = clicks.parse::<i32>().unwrap();
        return match direction {
        "L" => SafeMove { direction: Direction::L, clicks: clicks_i },
        _ => SafeMove { direction: Direction::R, clicks: clicks_i }
        };
    }).unwrap();
    
    return cols;
}

pub fn part1(filename : &str) -> i32 {
    let operations = parse_input(filename);
    
    let mut dial = 50;
    let mut zeros = 0;
    
    for r in operations {
        let mul = match r.direction {
          Direction::L => -1,
          Direction::R => 1
        };
        dial = (dial + mul * r.clicks) % 100;
        if dial == 0 {
          zeros += 1;
        }
    }

    return zeros;
}

#[cfg(test)]
mod tests {

    use super::*;
    
    #[test]
    fn test_part1() {
        let r = part1("sample-01.txt");
        assert_eq!(r, 3);
    }
    
    #[test]
    fn test_part2() {
        let r = part2("sample-01.txt");
        assert_eq!(r, 6);
    }

}

pub fn part2(filename : &str) -> i32 {
    let operations = parse_input(filename);
    
    let mut dial = 50;
    let mut zeros = 0;
    
    for r in operations {
      zeros += r.clicks / 100;
      let mult = match r.direction {
        Direction::L => -1,
        Direction::R => 1
      };
      
      // rem_quclid: Calculates the least nonnegative remainder of self (mod rhs).
      // kind of like a fancy % but deals with negative numbers
      let np = (dial + mult * r.clicks).rem_euclid(100);
      match r.direction {
		  Direction::L if dial != 0 => if np == 0 { zeros += 1; } else if np > dial { zeros += 1 }
		  Direction::R if dial != 0 => if np == 0 { zeros += 1; } else if np < dial { zeros += 1 }
		  _ => ()
      }
      dial = np;
    }
    
    return zeros;
}