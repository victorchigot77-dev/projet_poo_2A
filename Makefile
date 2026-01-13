# ============= CONFIG ==================

SRC_DIR = src
BIN_DIR = bin
LIBS    = lib/gui.jar
CP      = $(BIN_DIR):$(LIBS)

# ------------- SPECIFIC_SRC ------------
INVADER_SRC = $(SRC_DIR)/TestInvader.java 
BALLS_SRC = $(SRC_DIR)/TestBalls.java 
GRAVITYBALL_SRC = $(SRC_DIR)/TestGravityBall.java 
CELLULARSIM_SRC = $(SRC_DIR)/cellularSim/*.java
SWARMSIM_SRC = $(SRC_DIR)/SwarmSim/*.java
# ============= RULES ===================

all: build

# Compile ALL sources in src/ (preserves package structure)
build:
	javac -d $(BIN_DIR) -classpath $(LIBS) -sourcepath $(SRC_DIR) $(SRC_DIR)/**/*.java

# ================== COMPILER ===================
SPECIFIC_COMPILE_CMD = javac -d $(BIN_DIR) -classpath $(LIBS) 

compileInvader: 
	 $(SPECIFIC_COMPILE_CMD) $(INVADER_SRC) 
compileTestBalls: 
	 $(SPECIFIC_COMPILE_CMD) $(BALLS_SRC) 
compileTestGravityBall: 
	 $(SPECIFIC_COMPILE_CMD) $(GRAVITYBALL_SRC) 
compileCellularSim: 
	 $(SPECIFIC_COMPILE_CMD) $(CELLULARSIM_SRC) 
compileSwarmSim: 
	 $(SPECIFIC_COMPILE_CMD) $(SWARMSIM_SRC) 
# ================== RUNNERS ====================
RUN_CMD = java -classpath $(CP)

runInvader: compileInvader
	$(RUN_CMD) TestInvader

runTestBalls: compileTestBalls
	$(RUN_CMD) TestBalls

runTestGravityBall: compileTestGravityBall
	$(RUN_CMD) TestGravityBall

runGameOfLife: compileCellularSim
	$(RUN_CMD) cellularSim.GameOfLife

runGameOfImmigration:  compileCellularSim
	$(RUN_CMD) cellularSim.GameOfImmigration

runSegregationSim:  compileCellularSim
	$(RUN_CMD) cellularSim.SegregationSim

runSwarm: compileSwarmSim
	$(RUN_CMD) SwarmSim.SwarmSim

# ================== HOUSEKEEPING ================

clean:
	rm -rf $(BIN_DIR)/*

