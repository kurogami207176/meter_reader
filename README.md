# Meter Ingester

## Overview
This application expects NEM12 formatted CSV file.
These files are converted into insert statements,
There examples present in the `test/resources` directory.

## Notes
- Two reader class present: Serialised and Generator type.
- Service classes and tests are present.
- No entry point is present since there are several ways the source file could come in and unless defined, it's not worth pursuing.
- Logic is tested in code.
- Main logic in in MainService and other services are wired through that.