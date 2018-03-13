% Funzioni che imlementano metodi di newton e quasi-newton
% input: f(x), f'(x), punto di innesco, tolleranza
% n.b.: nessun escape su numero max iterazioni!

function [radice,iterazioni]=newton(f,f1,x0,tolx)
	fx = feval(f, x0);
	f1x = feval(f1, x0);
	radice = x0 - fx/f1x;
	iterazioni = 0;
	while ( abs(radice-x0)>tolx )
		iterazioni = iterazioni+1;
		x0 = radice;
		fx = feval(f, x0);
		f1x = feval(f1, x0);
		radice = x0 -fx/f1x;
	end
end


function [radice, iterazioni]=secanti(f,f1,x0,tolx)
	fx = feval(f, x0);
	f1x = feval(f1, x0);
	radice = x0 - fx/f1x;
	iterazioni = 0;
	while ( abs(radice-x0)>tolx )
		iterazioni = iterazioni+1;
		fx0 = fx;
		fx = feval(f,x);
		x1 = (fx*x0-fx0*x)/(fx-fx0);
		x0 = radice;
		radice = x1;
	end
end
