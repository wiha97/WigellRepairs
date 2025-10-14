#!/bin/sh

dockbuild() {
  img="$(echo "$(basename $(pwd))" | awk '{print tolower($0)}')"
  # img=$(pwd)
  tag=latest
  src=.
  run=0
  net=""
  name=""
  port=""
  mode="--rm"
  while getopts "i:t:s:rn:p:N:d" opt; do
    case $opt in
      i)
        img=$OPTARG
        ;;
      t)
        tag=$OPTARG
        ;;
      s)
        src=$OPTARG
        ;;
      r)
        run=1
        ;;
      n)
        net="--network $OPTARG"
        ;;
      N)
        name="--name $OPTARG"
        ;;
      p)
        val=$OPTARG
        if [[ ! $val =~ ":" ]]; then
          val="$val:$val"
        fi
        port="-p $val"
        ;;
      d)
        mode="-d"
        ;;
      *)
        echo -e """
        Options:
        -i <name>\t\t\timage name
        -t <testing>\t\t\ttag
        -s <dir>\t\t\tsource
        -r\t\t\t\tRun after successful build
        -d\t\t\t\tRun in background (detach)
        -n <network>\t\t\tNetwork name
        -N <name>\t\t\tinstance name
        -p <port>/<local>:<cont>\t Port
        """
        return 1
        ;;
    esac
  done
  img="$(echo "${img##*/}" | awk '{print tolower($0)}')"
  if docker buildx build -Dt "$img":"$tag" "$src"; then
    if [[ $run -eq 1 ]]; then
      cmd="docker run $mode $port $name $net $img:$tag"
      echo "running $cmd"
      eval $cmd
    fi
  else
    echo "build failed"
    return 1
  fi
}

dockbuild $@
