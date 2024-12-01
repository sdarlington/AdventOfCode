package day01

import (
	"bufio"
	"os"
	"log"
	"strconv"
	"sort"
	"strings"
	"fmt"
)

func parseInput(filename string)(columna []int, columnb []int)  {
  file, err := os.Open(filename)
  if err != nil {
  	log.Fatal(err)
  }
  defer file.Close()
  
  scanner := bufio.NewScanner(file)
 
  for scanner.Scan() {
    each_line := scanner.Text()
    
    values := strings.Fields(each_line)
    colaInt, err1 := strconv.Atoi(values[0])
    colbInt, err2 := strconv.Atoi(values[1])
    if (err1 != nil || err2 != nil) {
        fmt.Println("Non-integer values?")
        continue
    }
    columna = append(columna, colaInt)
    columnb = append(columnb, colbInt)
  }
  
  sort.Ints(columna)
  sort.Ints(columnb)

  return columna, columnb
}

func day1(inputFilename string) (result []int) {

  cola, colb := parseInput(inputFilename)

  result = cola[:]
  for i := 0; i < len(cola); i++ {
      diff := cola[i] - colb[i]
      if diff < 0 {
        diff = -diff
      }
      result[i] = diff
  }

  return result
}

func Part1(inputFilename string) int {
    result := day1(inputFilename)
    c := 0
	for i := 0; i < len(result); i++ {
	    c += result[i]
	}
    return c
}

func Part2(inputFilename string) int {
  cola, colb := parseInput(inputFilename)
  
  m := make(map[int]int)
  for i := 0; i < len(colb); i++ {
    value := colb[i]
    v, ok := m[value]
    if !ok {
      m[value] = 1
    } else {
      m[value] = v + 1
    }
  }
  
  result := 0
  
  for i := 0; i < len(cola); i++ {
    v, ok := m[cola[i]]
    if ok {
      result += v * cola[i]
    }
  }
  
  return result
}

