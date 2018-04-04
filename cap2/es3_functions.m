%
% rispetto al metodo di newton standard, e' stato aggiunto
% il parametro di imput m, che rappresenta il
% coefficiente del termine di correzione
%
function [radice, it, valf, valf1] = newton_m(f, f1, x0, m, tolx, itmax)
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
        radice = x0 - m*(fx/f1x);
    end

    if abs(radice-x0) > tolx
        error('non converge')
    end
end

function [radice, it, valf, val1] = aitken(f, f1, x0, tolx, imax)
	fx = feval(f, x0);
	f1x = feval(f1, x0);
	radice = x0 - fx/f1x;
	it = 0;
    valf = 1;
    val1 = 1;
	while (abs(radice-x0) > tolx) && (it < imax)
        it = it+1;
        x0 = radice;
        fx = feval(f, x0);
        f1x = feval(f1, x0);
        x1 = x0 - fx/f1x;
        fx = feval(f, x1);
        f1x = feval(f1, x1);
        valf = valf + 2;
        val1 = val1 + 2;
        radice = x1 - fx/f1x;
        radice = (radice*x0 - x1^2)/(radice-2*x1+x0);
    end
end
