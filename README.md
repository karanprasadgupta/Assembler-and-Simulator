# Assembler and Simulator

This repository contains an Assembler and Simulator for a specific architecture. The project aims to convert assembly language code into machine code and simulate the execution of the resulting machine code.

The Assembler inputs assembly language code and generates the corresponding machine code, which the Simulator can execute. The Simulator emulates the execution of the machine code, allowing you to observe the proper execution of instructions.

## Table of Contents
- [Code](#code)
- [Installation](#installation)
- [Evaluation](#evaluation)
- [Supported Instructions](#supported-instructions)
- [Contributing](#contributing)
- [License](#license)

## Code
* The assembler code is in the `Simple-Assembler` directory. Commands to execute the Assembler is in `Simple-Assembler/run`.
* The simulator code in the `SimpleSimulator` directory. Commands to execute the Simulator is in `SimpleSimulator/run`.
* The Assembler and the Simulator read from `stdin`.
* The Assembler and the Simulator write to `stdout`.
  
## Installation

To install and set up the project locally, follow these steps:

1. Clone the repository:
   ```shell
   git clone https://github.com/karanprasadgupta/Assembler-and-Simulator.git
   ```
2. Navigate to the project directory:
   ```shell
   cd Assembler-and-Simulator
   ```
## Evaluation
### How to evaluate
* Go to the `automatedTesting` directory and execute the `run` file with appropiate options passed as arguments.
* Options available for automated testing:
	1. `--verbose`: Prints verbose output
	2. `--no-asm`: Does not evaluate the assembler
	3. `--no-sim`: Does not evaluate the simulator
## Supported Instructions
The Assembler and Simulator support a specific set of assembly language instructions. You can refer to the [Assignment.pdf](https://github.com/karanprasadgupta/Assembler-and-Simulator/blob/main/Assignment.pdf) for detailed information about  the list of instructions and their usage.

## Contributing
Contributions are welcome! If you find any issues or have suggestions for improvements, please open an issue or submit a pull request. Make sure to follow the repository's code of conduct.

## License
This project is licensed under the [MIT License](./LICENSE). Feel free to modify and use the code as per the license terms.

>This project was developed just for learning purposes as part of the Computer Organization, Monsoon 2021 semester, assignment at IIITD.
