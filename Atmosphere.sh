#!/bin/bash
rm -rf tmp/*
scala -J-Xmx8144M -J-d64 Atmosphere.jar &
xdg-open https://localhost:50777
