#!/bin/sh
export PATH="$(dirname $(readlink -f $0)):$PATH"
"$@"
