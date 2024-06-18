--  3enraya.al
------------------------------------------------------------------------
procedure tres_en_raya is
        
    partidaFinalizada: boolean;
    esTurnoIA: boolean;
    iaMaximiza: boolean;
    cJugador: character;
    tablero: array(0..8) of character;

--------------------------------------------------------------------
procedure inicializarTablero (t: ref array(0..8) of character) is
--------------------------------------------------------------------
        i,j: integer;
begin
    i:=0;
    while i<3 loop
        j:=0;
        while j<3 loop
            t(i*3+j) := ' ';
            j:=j+1;
        end loop;
        
        i:=i+1;
    end loop;
end;

--------------------------------------------------------------------
procedure imprimirTablero (t: ref array(0..8) of character) is
--------------------------------------------------------------------
    i,j: integer;
begin
    i:=0;
  put_line("-------");
    while i<3 loop
        j:=0;
        put('|');
        while j<3 loop
            put(t(i*3+j),'|');
            j:=j+1;
        end loop;
                
        put_line;
        put_line("-------");

        i:=i+1;
    end loop;
end;

--------------------------------------------------------------------
function comprobarVictoria (t: ref array(0..8) of character;player: character) return boolean is
--------------------------------------------------------------------
    i,j:integer;
begin
    i:=0;
        
    while i<3 loop
        if t(i*3+0)=player and t(i*3+1)=player and t(i*3+2)=player then
            return true;
        end if;
        
        if t(0*3+i)=player and t(1*3+i)=player and t(2*3+i)=player then
            return true;
        end if;
        
        i:=i+1;
    end loop;
        

    i:=0;
    j:=0;
    if(t(i*3+j)=player) then
        i:=1;
        j:=1;
        if(t(i*3+j)=player) then
            i:=2;
            j:=2;
            if(t(i*3+j)=player) then
                return true;
            end if;
        end if;
    end if;
  
    i:=2;
    j:=0;
    if t(i*3+j)=player then
        i:=1;
        j:=1;
        if(t(i*3+j)=player) then
            i:=0;
            j:=2;
            if(t(i*3+j)=player) then
                return true;
            end if;
        end if;
    end if;
    return false;
end;

--------------------------------------------------------------------
function comprobarEmpate (t: ref array(0..8) of character) return boolean is
--------------------------------------------------------------------
    i:integer;
begin
    i:=0;
    
    while i<3 loop
        if t(i*3+0)=' ' or t(i*3+1)=' ' or t(i*3+2)=' ' then
            return false;
        end if;
        i:=i+1;
    end loop;
    return true;
end;

--------------------------------------------------------------------
function minimax (t: ref array(0..8) of character;estaMaximizando: boolean) return integer is
--------------------------------------------------------------------
    i,j,score,bestScore:integer;
begin
    score:= 0;
    
    if comprobarVictoria(t,'O') then
            return 2;
    elsif comprobarVictoria(t,'X') then
            return 0;
    elsif comprobarEmpate(t) then
            return 1;
    end if;
    
    if estaMaximizando then
        bestScore := 0;
        i:=0;
        while i<3 loop
            j:=0;
            while j<3 loop
                    if t(i*3+j)=' ' then
                            t(i*3+j):='O';
                            score:=minimax(t,false);
                            t(i*3+j):=' ';
                            if score>bestScore then
                                    bestScore:=score;
                            end if;        
                    end if;
                    
                    j:=j+1;
            end loop;
            if bestScore=2 then
                j:=3;
                i:=3;
            end if;
            i:=i+1;
        end loop;
    else
            bestScore := 2;
            i:=0;
            while i<3 loop
                    j:=0;
                    while j<3 loop
                            if t(i*3+j)=' ' then
                                    t(i*3+j):='X';
                                    score:=minimax(t,true);
                                    t(i*3+j):=' ';
                                    if score<bestScore then
                                            bestScore:=score;
                                    end if;        
                            end if;
                            if bestScore=0 then
        j:=3;
        i:=3;
    end if;
                            j:=j+1;
                    end loop;
                    
                    i:=i+1;
            end loop;
    end if;
    
    return bestScore;
end;

--------------------------------------------------------------------
procedure iaMove (t: ref array(0..8) of character;estaMaximizando: boolean) is
--------------------------------------------------------------------
        i,j,score,bestScore,bestMoveRow,bestMoveCol:integer;
begin
        score:= 0;
  
  if estaMaximizando then
          bestScore := 0;
          
          i:=0;
          while i<3 loop
                  j:=0;
                  while j<3 loop
                          if t(i*3+j)=' ' then
                                  t(i*3+j):='O';
                                  score:=minimax(t,false);
                                  t(i*3+j):=' ';
                                  if score>bestScore then
                                          bestScore:=score;
                                          bestMoveCol := j;
                                          bestMoveRow := i;
                                  end if;        
                          end if;
                          if bestScore=2 then
          j:=3;
          i:=3;
        end if;
                          j:=j+1;
                  end loop;
                  
                  i:=i+1;
          end loop;
   t(bestMoveRow*3+bestMoveCol) := 'O';
  else
    bestScore := 2;
          
          i:=0;
          while i<3 loop
                  j:=0;
                  while j<3 loop
                          if t(i*3+j)=' ' then
                                  t(i*3+j):='X';
                                  score:=minimax(t,true);
                                  t(i*3+j):=' ';
                                  if score<bestScore then
                                          bestScore:=score;
                                          bestMoveCol := j;
                                          bestMoveRow := i;
                                  end if;        
                          end if;
                          if bestScore=0 then
          j:=3;
          i:=3;
        end if;
                          j:=j+1;
                  end loop;
                  
                  i:=i+1;
          end loop;
   t(bestMoveRow*3+bestMoveCol) := 'X';
  end if;
        
        
        put_line("La IA ha jugado en la fila ",bestMoveRow+1," y columna ",bestMoveCol+1);
end;

--------------------------------------------------------------------
procedure playerMove (t: ref array(0..8) of character;jugador: character) is
--------------------------------------------------------------------
        row,col:integer;
begin
        put_line("Introduzca la fila (0-2)");
        get(row);
        put_line("Introduzca la columna (0-2)");
        get(col);
        
        if row>=0 and row <3 and col>=0 and col<3 and t(row*3+col)=' ' then
                t(row*3+col) := jugador;
        else
                put_line("Movimiento invalido.");
                playerMove(t,jugador);
        end if;
        
end;

--------------------------------------------------------------------
function playerElection return boolean is
--------------------------------------------------------------------
        p:character;
begin
        put_line("Introduzca su ficha(X/O)");
        get(p);
        
        if p='X' then
                return false;
        elsif p='O' then
    return true;
  else
                put_line("Ficha invalida.");
                return playerElection;
        end if;
        
end;

-----------------------------------------------------------
begin
        inicializarTablero(tablero);
        
        esTurnoIA:=playerElection;
        partidaFinalizada:=false;
 
  iaMaximiza:=not esTurnoIA;
  
  if esTurnoIA then
    cJugador := 'O';
  else
    cJugador := 'X';
  end if;
        
        while not partidaFinalizada loop
                imprimirTablero(tablero);
                
                if esTurnoIA then
                        iaMove(tablero,iaMaximiza);
                else
                        playerMove(tablero,cJugador);
                end if;
                
                if comprobarVictoria(tablero,'X') then
                        imprimirTablero(tablero);
                        put_line("Ganaron X");
                        partidaFinalizada:=true;
                elsif comprobarVictoria(tablero,'O') then
                        imprimirTablero(tablero);
                        put_line("Ganaron O");
                        partidaFinalizada:=true;
                elsif comprobarEmpate(tablero) then
                        imprimirTablero(tablero);
                        put_line("Empate.");
                        partidaFinalizada:=true;
                end if;
                
                esTurnoIA := not esTurnoIA;
                        
        end loop;
        
end;