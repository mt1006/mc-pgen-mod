# About
**ParticleGenerator** is a Minecraft mod created mainly for builders and mapmakers. It adds particle generator block into the game.

The block is highly customisable and using it is also much more convenient and efficient than using command blocks. It allows you to generate multiple particles from one block and set generated particles position and speed randomization.


# Usage

### Commands
```
/pgen show - Shows edges of particle generators
/pgen hide - Hides edges of particle generators
/pgen locate - Adds markers inside particle generators
/pgen info - Displays information about mod
/pgen help - Displays the help message with 
```

### Particle Generator block states
```
position=[center(default)/top/bottom] - determines position of particles generation
```

### Particle Generator NBT tags
```
Particle Generator NBT tags:
  Particles:[{...}] - list of compounds:
    id:"" - particle id
    Motion:[x,y,z] - particle velocities [blocks/tick] (in most cases)
    MotionRand:[x,y,z] - randomization of "Motion" values
    PositionOffset:[x,y,z] - offset of particle positions
    PositionRand:[x,y,z] - randomization of particle positions
    Interval:int - interval between particles [ticks]
    Probability:double - probability of particle spawning
    ParticleCount:int - number of particles when spawned
    ParticleMaxCount:int - maximum number of particles
    AdditionalTags:{} - additional tags (like block id)
  UseAnimateTick:bool - spawn particles on animateTick
```

### Useful Minecraft BlockItem NBT tags
```
BlockStateTag:{} - specifies block states
BlockEntityTag:{} - specifies block NBT tags
```