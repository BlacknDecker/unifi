% Funzioni che imlementano metodi di newton modificato e di accelerazione di Aitken

% n.b.: nessun escape su numero max iterazioni!

% input: f(x), f'(x), molteplicita radice, punto di innesco, tolleranza
function [radice,iterazioni]=newton_mod(f,f1,m,x0,tolx)
	fx = feval(f, x0);
	f1x = feval(f1, x0);
	radice = x0 - fx/f1x;
	iterazioni = 0;
	while ( abs(radice-x0)>tolx )
		iterazioni = iterazioni+1;
		x0 = radice;
		fx = feval(f, x0);
		f1x = feval(f1, x0);
		radice = x0 - m*(fx/f1x);
	end
end

function [radice, iterazioni] = aitken( f, f1, x0, tolx )
	fx = feval(f, x0);
	f1x = feval(f1, x0);
	radice = x0 - fx/f1x;
	iterazioni = 0;
	while abs(radice-x0)>tolx
        iterazioni = iterazioni+1;
        x0 = radice;
        fx = feval(f, x0);
        f1x = feval(f1, x0);
        x1 = x0 -fx/f1x;
        fx = feval(f, x1);
        f1x = feval(f1, x1);
        radice = x1 -fx/f1x;
        radice = (radice*x0 - x1^2)/(radice-2*x1+x0);
    end
end
