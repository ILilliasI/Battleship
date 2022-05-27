# Battleship game for single-player

1. Initial command line input template:
rowsNumber(from 1 to 100) columnsNumber(from 1 to 100) carriersNumber battleshipsNumber cruisersNumber destroyersNumber submarinesNumber enableTorpedoMode(1 - enable, 0 - don't enable) enableRecoveryMode(1 - enable, 0 - don't enable) torpedoesNumber(max=shipsNumber)
Sum of all cells occupied by ships numbers must be <= 1/4 of game  table size and > 0.

e.g. 7 7 2 0 0 1 0 0 1 0
Game table 7x7 with 2 carriers and 1 destroyer, recovery mode enabled.

2. Input game table parameters from console:
rows count - from 1 to 100
columns count - from 1 to 100

3. Input ships parameters from console:
carriers number >= 0
battleships number >= 0
cruisers number >= 0
destroyers number >= 0
submarines number >= 0
Sum of all cells occupied by ships numbers must be <= 1/4 of game  table size and > 0.

4. Input modes parameters from console:
torpedo firing mode - 0(enable) or 1(do not enable)
recovery mode - 0(enable) or 1(do not enable)
torpedoes count - from 0 to fleet ships number (if 0 entered and torpedo firing mode is enabled, torpedo mode is disabled)

5. Input shot coordinates:
x coordinate - from 0 to rows number minus 1
y coordinate - from 0 to columns number minus 1
Enter T to use torpedo
