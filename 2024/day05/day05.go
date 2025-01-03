package day05

import (
	"aoc2024/helper"
	"log"
	"strconv"
	"strings"
)

type Rules struct {
	ruleMap map[int][]int
}

func (r Rules) findSecondFromFirst(first int) (result []int) {
	return r.ruleMap[first]
}

func parseInput(filename string) (pageRules Rules, pageUpdates [][]int) {
	output := helper.ParseInput(filename, func(each_line string) (reports string) {
		return each_line
	})

	updates := false
	pageRules.ruleMap = make(map[int][]int)
	for _, line := range output {
		if line == "" {
			updates = true
		} else if !updates {
			fields := strings.Split(line, "|")
			if len(fields) == 2 {
				k, _ := strconv.Atoi(fields[0])
				v, _ := strconv.Atoi(fields[1])
				r, o := pageRules.ruleMap[k]
				if o {
					r = append(r, v)
					pageRules.ruleMap[k] = r
				} else {
					pageRules.ruleMap[k] = []int{v}
				}
			} else {
				log.Fatal("Wrong number of fields ", fields)
			}
		} else {
			fields := strings.Split(line, ",")
			o := make([]int, len(fields))
			for i, f := range fields {
				v, _ := strconv.Atoi(f)
				o[i] = v
			}
			pageUpdates = append(pageUpdates, o)
		}
	}

	return
}

func checkOrdering(updates []int, rules Rules) (bool, int) {
	for i, p := range updates {
		req := rules.findSecondFromFirst(p)
		for _, r := range req {
			for ix := 0; ix < len(updates); ix++ {
				if ix < i && updates[ix] == r {
					return false, ix
				}
			}
		}
	}
	return true, 0
}

func Part1(inputFilename string) (result int) {
	rules, updates := parseInput(inputFilename)

	for _, u := range updates {
		success, _ := checkOrdering(u, rules)
		if success {
			result += u[len(u)/2]
		}
	}

	return
}

func Part2(inputFilename string) (result int) {
	rules, updates := parseInput(inputFilename)

	ch := make(chan int)
	for _, u := range updates {
		go func() {
			iteration := 0
			r := 0
			for {
				success, errorIndex := checkOrdering(u, rules)
				if success && iteration == 0 {
					break
				} else if success && iteration > 0 {
					r += u[len(u)/2]
					break
				}
				iteration++
				u[errorIndex], u[errorIndex+1] = u[errorIndex+1], u[errorIndex]
				if iteration > 50 {
					r = -1
					break
				}
			}
			ch <- r
		}()
	}

	for i := 0; i < len(updates); i++ {
		result += <-ch
		log.Println(i, " / ", len(updates))
	}

	return
}
