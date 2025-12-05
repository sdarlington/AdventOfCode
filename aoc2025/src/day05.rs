
#[derive(Debug)]
struct Range {
  from : i64,
  to   : i64
}

enum ParseInput {
  Blank,
  Range(i64, i64 ),
  Ingredient (i64)
}

fn parse_input(filename : &str) -> (Vec<Range>, Vec<i64>) {
    let cols = crate::helper::parse_input(filename, |line| {
        if line.eq("") {
            return ParseInput::Blank;
        }
        let mut elements = line.split("-");
        let first = elements.next().unwrap();
        let second = elements.next();
        return match second {
        None => ParseInput::Ingredient(first.parse::<i64>().unwrap()),
        Some(e) => ParseInput::Range(first.parse::<i64>().unwrap(), e.parse::<i64>().unwrap()),
        };
    }).unwrap();
    
    let range = cols.iter().map(|x| match x { ParseInput::Range(a,b) => Range{from:*a, to: *b}, _ => Range {from:-1, to: 0} })
                           .filter(|x| x.from != -1)
                           .collect();
    let ingredients = cols.iter().map(|x| match x { ParseInput::Ingredient(a) => *a, _ => -1 })
                           .filter(|x| *x != -1)
                           .collect();

    return (range, ingredients);
}

pub fn part1(filename : &str) -> i64 {
    let (ranges, ingredients) = parse_input(filename);
    
    let mut total : i64 = 0;
    for i in ingredients {
        for r in &ranges {
            if i >= r.from && i <= r.to {
                total +=1 ;
                break;
            }
        }
    }
    
    return total;
}

#[cfg(test)]
mod tests {

    use super::*;
    
    #[test]
    fn test_part1() {
        let r = part1("sample-05.txt");
        assert_eq!(r, 3);
    }
    
    #[test]
    fn test_part2() {
        let r = part2("sample-05.txt");
        assert_eq!(r, 14);
    }

}

pub fn part2(filename : &str) -> i64 {
    let (mut ranges, _ingredients) = parse_input(filename);
    
    let mut total : i64 = 1;
    ranges.sort_by_key(|x| x.from);
    let mut last_to = ranges[0].from;
    for r in ranges {
        let mut count = r.to - r.from + 1 ;

        // if the ranges overlap        
        if last_to >= r.from {
            count -= last_to - r.from + 1;
        }
        
        // if there are no new numbers to count
        if count > 0 {
            total += count;
        }
        if r.to > last_to {
            last_to = r.to;
        }
    }

    return total;
}