package day17

import (
	"strconv"
	"log"
	"os"
	"bufio"
	"regexp"
	"strings"
	"math"
)

type cpu struct {
	a,b,c int // registers
	program []int
	pc int
	output []int
}

func parseInput(filename string) (output cpu) {
	regex := regexp.MustCompile("^(Register (A|B|C): (\\d+)|Program: ([,\\d]+))$")

	file, err := os.Open(filename)
	if err != nil {
		log.Fatal(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		line := scanner.Text()

		parsed := regex.FindAllStringSubmatch(line,-1)
		//log.Println(parsed)
		if len(parsed) == 0 {
			continue
		}
		instr := parsed[0]
		val,_ := strconv.Atoi(instr[3])
		switch instr[2] {
		case "A":
			output.a = val
		case "B":
			output.b = val
		case "C":
			output.c = val
		default:
			opcodes := strings.Split(instr[4], ",")
			for _,v := range opcodes {
				io,_ := strconv.Atoi(v)
				output.program = append(output.program, io)
			}
		}
	}

	return
}

func combo(cpu *cpu) int {
	val := cpu.program[cpu.pc + 1]
	switch val {
	case 0,1,2,3:
		return val
	case 4:
		return cpu.a
	case 5:
		return cpu.b
	case 6:
		return cpu.c
	default:
		return -1
	}
}

func powInt(x,y int) int {
	return int(math.Pow(float64(x), float64(y)))
}

func next(program *cpu) {
	switch program.program[program.pc] {
	case 0: //adv
		program.a = program.a / powInt(2, combo(program))
	case 1: // bxl
		program.b = program.b ^ program.program[program.pc + 1]
	case 2: //bst
		program.b = combo(program) % 8
	case 3: // jnz
		if program.a != 0 {
			program.pc = program.program[program.pc + 1] - 2
		}
	case 4: //bxc
		program.b = program.b ^ program.c
	case 5: //out
		program.output = append(program.output, combo(program) % 8)
	case 6: //bdv
		program.b = program.a / powInt(2, combo(program))
	case 7: //cdv
		program.c = program.a / powInt(2, combo(program))
	}
	program.pc += 2
}

func genOutput(cpu cpu) string {
	var builder strings.Builder

	for i,v := range cpu.output {
		if i > 0 {
			builder.WriteString(",")
		}
		builder.WriteString(strconv.Itoa(v))
	}
	
	return builder.String()
}

func Part1(inputFilename string) (result string) {
	cpu := parseInput(inputFilename)

	for cpu.pc < len(cpu.program) {
		next(&cpu)
	}

	result = genOutput(cpu)
	
	return
}

