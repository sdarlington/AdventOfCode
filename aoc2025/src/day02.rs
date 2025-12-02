

#[derive(Debug)]
struct Range {
  from : i64,
  to   : i64
}

fn parse_input(filename : &str) -> Vec<Range> {
    let mut cols = crate::helper::parse_input(filename, |line| {
        let mut out : Vec<Range> = Vec::new();
        for r in line.split(",") {
			let x : Vec<&str> = r.split("-").collect();
			let from = x[0].parse::<i64>().unwrap();
			let to = x[1].parse::<i64>().unwrap();
			out.push(Range { from, to });
        }
        return out;
    }).unwrap();
    
    return cols.pop().unwrap();
}

fn test_range(range : Range) -> Vec<i64> {
  let mut invalid : Vec<i64> = Vec::new();
  for n in range.from .. range.to+1 {
    let ns = n.to_string();
    let (left,right) = ns.split_at(ns.len() / 2);
    if left.eq(right) {
        invalid.push(n);
    }
  }
  return invalid;
}

pub fn part1(filename : &str) -> i64 {
    let operations = parse_input(filename);

    let mut result : i64 = 0;
    
    for o in operations {
      for r in test_range(o) {
        result += r;
      }
    }

    return result;
}

#[cfg(test)]
mod tests {

    use super::*;

    #[test]
    fn test_range_1() {
        assert_eq!(test_range(Range { from: 11, to: 22}).len(), 2);
    }

    #[test]
    fn test_range_2() {
        assert_eq!(test_range(Range { from: 95, to: 115}).len(), 1);
    }

    #[test]
    fn test_range_3() {
        assert_eq!(test_range(Range { from: 998, to: 1012}).len(), 1);
    }

    #[test]
    fn test_range_4() {
        assert_eq!(test_range(Range { from: 1188511880, to: 1188511890}).len(), 1);
    }
    
    #[test]
    fn test_part1() {
        let r = part1("sample-02.txt");
        assert_eq!(r, 1227775554);
    }
    
    #[test]
    fn test_part2() {
        let r = part2("sample-02.txt");
        assert_eq!(r, -1);
    }

}

pub fn part2(_filename : &str) -> i64 {
    return -1;
}