
fn parse_input(filename : &str) -> Vec<Vec<i8>> {
    let cols = crate::helper::parse_input(filename, |line| {
        let elements = line.chars().map(|x| x.to_string().parse::<i8>().unwrap()).collect();
        return elements;
    }).unwrap();
    
    return cols;
}

fn find_highest(list : &[i8], exclude_last : bool) -> (i8,usize) {
    let mut highest : i8 = 0;
    let mut idx : usize = 0;
    for (i,el) in list.iter().enumerate() {
      if i == list.len()-1 && exclude_last {
          break;
      }
      if *el > highest {
          highest = *el;
          idx = i;
      }
    }
    return (highest,idx);
}

pub fn part1(filename : &str) -> i32 {
    let batteries = parse_input(filename);
    
    let mut total : i32 = 0;
    for b in batteries {
        let (first_digit,idx) = find_highest(&b, true);
        let (second_digit,_idx) = find_highest(&b[idx+1..], false);
        let jolts = first_digit * 10 + second_digit;
        total += i32::from(jolts);
    }

    return total;
}

#[cfg(test)]
mod tests {

    use super::*;
    
    #[test]
    fn test_part1() {
        let r = part1("sample-03.txt");
        assert_eq!(r, 357);
    }
    
    #[test]
    fn test_part2() {
        let r = part2("sample-03.txt");
        assert_eq!(r, 6);
    }

}

pub fn part2(filename : &str) -> i32 {
    let _operations = parse_input(filename);
        
    return -1;
}