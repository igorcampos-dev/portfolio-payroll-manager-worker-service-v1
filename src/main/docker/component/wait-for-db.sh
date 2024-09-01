#!/bin/sh
# wait-for-db.sh

set -e

mysql_hostport="$1"

shift 2
# shellcheck disable=SC2124
cmd="$@"

mysql_host=$(echo "$mysql_hostport" | cut -d':' -f1)
mysql_port=$(echo "$mysql_hostport" | cut -d':' -f2)

C_RESET='\e[0m'
C_CYAN='\e[36m'
C_GREEN='\e[32m'
C_PURPLE='\033[35m'

wait_for_service() {

    # shellcheck disable=SC2039
    local host="$1"
    # shellcheck disable=SC2039
    local port="$2"

    until nc -z "$host" "$port"; do

        # shellcheck disable=SC2039
        >&2 echo -e "${C_CYAN}bash${C_RESET} ${C_PURPLE}$(date +'%H:%M:%S')${C_RESET} ${C_GREEN}INFO${C_RESET} ==> Aguardando $host:$port ficar disponível..."
        sleep 6
    done

    # shellcheck disable=SC2039
    >&2 echo -e "${C_CYAN}bash${C_RESET} ${C_PURPLE}$(date +'%H:%M:%S')${C_RESET} ${C_GREEN}INFO${C_RESET} ==> $host:$port está pronto!"
}

wait_for_service "$mysql_host" "$mysql_port"

# shellcheck disable=SC2039
>&2 echo -e "${C_CYAN}bash${C_RESET} ${C_PURPLE}$(date +'%H:%M:%S')${C_RESET} ${C_GREEN}INFO${C_RESET} ==> Serviços disponíveis! Iniciando a API..."

# shellcheck disable=SC2086
exec $cmd