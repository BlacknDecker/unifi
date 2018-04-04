%
% tutte e tre le funzioni riportate sotto hanno la
% stessa seguente interfaccia
%
% input: funzione, derivata prima, punto di innesco,
%        tolleranza, numero massimo iterazioni
% output: radice approssimata, numero di iterazioni eseguite,
%         numero valutazioni di f(x) e di f'(x)
%

function [radice, it, valf, valf1] = newton(f, f1, x0, tolx, itmax)
    fx = feval(f, x0);
    f1x = feval(f1, x0);
    radice = x0 - fx/f1x;
    it = 0;
    valf = 1;
    valf1 = 1;
    while (it < itmax) && (abs(radice-x0)>tolx)
        it = it + 1;
        x0 = radice;
        fx = feval(f, x0);
        f1x = feval(f1, x0);
        valf = valf + 1;
        valf1 = valf1 + 1;
        radice = x0 - fx/f1x;
    end

    if abs(radice-x0) > tolx
        error('non converge')
    end
end

function [radice, it, valf, val1] = corde(f, f1, x0, tolx, imax)
	it=0;
	f1x0 = feval(f1, x0);
	radice = x0 - feval(f, x0) / f1x0;
    valf = 1;
    val1 = 1;
	while (abs(radice-x0) > tolx) && (it < imax)
            it = it + 1;
			fx = feval(f, radice);
            valf = valf + 1;

			if abs(fx) <= (tolx * abs(f1x0))
				 break
            end

			x1 = radice - fx / f1x0;
			radice = x1;
	end

	if abs(radice-x0) > tolx
        error('non converge')
    end
end


function [radice, it, valf, val1] = secanti(f, f1, x0, tolx, imax)
	it = 0;
    fx = feval(f, x0);
    f1x = feval(f1, x0);
    valf = 1;
    val1 = 1;
    radice = x0 - fx / f1x;

    while (it < imax) && (abs(radice-x0) > tolx)
        it = it + 1;
        fx = feval(f, radice);
        fx0 = feval(f, x0);
        valf = valf + 2;
        x1 = (fx*x0 - fx0*radice) / (fx - fx0);
        x0 = radice;
        radice = x1;
    end

    if abs(radice-x0) > tolx
        error('non converge')
    end
end
