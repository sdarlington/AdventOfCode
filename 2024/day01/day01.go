package day01

import (
	"bufio"
	"log"
	"os"
	"sort"
	"strconv"
	"strings"
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
        log.Fatal("Non-integer value ", values[0], values[1])
        continue
    }
    columna = append(columna, colaInt)
    columnb = append(columnb, colbInt)
  }
  
  sort.Ints(columna)
  sort.Ints(columnb)

  return
}

func Part1(inputFilename string) (result int) {
  cola, colb := parseInput(inputFilename)

  result = 0
  for i := 0; i < len(cola); i++ {
      diff := cola[i] - colb[i]
      if diff < 0 {
        diff = -diff
      }
      result += diff
  }

  return
}

func Part2(inputFilename string) int {
  cola, colb := parseInput(inputFilename)
  
  m := make(map[int]int)
  for _, value := range colb {
    v, ok := m[value]
    if !ok {
      m[value] = 1
    } else {
      m[value] = v + 1
    }
  }
  
  result := 0
  
  for i, e := range cola {
    v, ok := m[e]
    if ok {
      result += v * cola[i]
    }
  }
  
  return result
}

