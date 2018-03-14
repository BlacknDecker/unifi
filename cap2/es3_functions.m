
function [y, i] = NewtonMod(f, df, m, x0, imax, tol, output)
	format long;
	i = 0;
	x = x0;
	vai=1;
	while((i < imax) && vai)
		i = i+1;
		fx = feval(f, x0);
		dfx = feval(df, x0);
		if(dfx ~= 0)
			x = x0 - m * fx / dfx;
		else
			break;
		end
		if(abs(x-x0)<tol)
			vai = 0;
		end
		x0 = x;
	end
	if(output)
		if(vai)
			disp('Impossibile calcolare la tolleranza richiesta nel numero di iter');
		else
		   disp(i);
		end
	end
	y = x;
end

function y = Aitken( f, df, x0, imax, tol )
	format long;
	i = 0;
	vai=1;

	while((i < imax) && vai)
		x1 = NewtonMod(f, df, 1, x0, 1, tol, 0);
		x2 = NewtonMod(f, df, 1, x1, 1, tol, 0);
		i = i+1;
		if((x0 - 2*x1 +x2) == 0)
			disp('Errore, impossibile dividere per 0');
			vai = 0;
			break;
		end
		x = (x2*x0 - x1^2)/(x0 - 2*x1 +x2);
		if(abs(x-x0)<tol)
			vai = 0;
		end
		x0 = x;
	end
	if(vai)
		disp('Impossibile calcolare la tolleranza richiesta nel numero di iter');
		disp(i);
	end
	y = x;
end
