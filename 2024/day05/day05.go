package day05

import (
	"aoc2024/helper"
	"log"
// 	"sort"
	"strconv"
	"strings"
)


type Rules struct {
    rules [][]int
}

func (r Rules) findSecondFromFirst(second int) (result []int) {
  for _,e := range r.rules {
    if e[0] == second {
      result = append(result, e[1])
    }
  }
  return
}

func parseInput(filename string) (pageRules Rules, pageUpdates [][]int) {
	output := helper.ParseInput(filename, func(each_line string) (reports string) {
		return each_line
	})

    updates := false
    for _,line := range output {
        if line == "" {
            updates = true
        } else if !updates {
        	fields := strings.Split(line, "|")
        	if len(fields) == 2  {
        	  val := make([]int, 2)
        	  val[0],_ = strconv.Atoi(fields[0])
        	  val[1],_ = strconv.Atoi(fields[1])
        	  pageRules.rules = append(pageRules.rules, val)
        	} else {
        	  log.Fatal("Wrong number of fields ", fields)
        	}
        } else {
            fields := strings.Split(line,",")
            o := make([]int, len(fields))
            for i,f := range fields {
            	v,_ := strconv.Atoi(f)
            	o[i] = v
            }
            pageUpdates = append(pageUpdates, o)
        }
    }	

	return
}

func Part1(inputFilename string) (result int) {
  rules, updates := parseInput(inputFilename)

  for _,u := range updates {
    fail := false
//     log.Println("***")
//     log.Println("Input: ", u)
    for i,p := range u {
        req := rules.findSecondFromFirst(p)
//         log.Println ("In: ", p, "Req: ", req)
        for _,r := range req {
            for ix := 0; ix < len(updates); ix++ {
                if ix < i && u[ix] == r {
//                     log.Println("Fail ", ix, " ", u[ix])
                    fail = true
                    break
                }
            }
        }
    }
    if !fail {
//         log.Println(u, "C ", u[len(u)/2])
        result += u[len(u)/2]
//     } else {
//         log.Println(u, "F ", 0)
    }
  }

  return
}
