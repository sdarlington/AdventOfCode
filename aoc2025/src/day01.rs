

#[derive(Debug)]
enum Direction { L { clicks : i32 } , R { clicks : i32 } }

fn parse_input(filename : &str) -> Vec<Direction> {
    let cols = crate::helper::parse_input(filename, |line| {
        let (direction,clicks) = line.split_at(1);
        let clicks_i = clicks.parse::<i32>().unwrap();
        return match direction {
        "L" => Direction::L { clicks: clicks_i },
        _ => Direction::R { clicks: clicks_i }
        };
    }).unwrap();
    
    return cols;
}

pub fn part1(filename : &str) -> i32 {
    let operations = parse_input(filename);
    
    let mut dial = 50;
    let mut zeros = 0;
    
    for r in operations {
        match r {
        Direction::L { clicks } => dial = (dial - clicks) % 100,
        Direction::R { clicks } => dial = (dial + clicks) % 100
        }
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
    fn test1() {
        let r = part1("sample-01.txt");
        assert_eq!(r, 3);
    }
}

pub fn part2(_filename : &str) -> i32 {
  return -1
}