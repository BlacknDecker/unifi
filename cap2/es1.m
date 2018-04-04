fun = @(x) (x)^3 - 4*(x)^2 + 5*x -2;
tolx = 1/10; % valori sempre minori
[zero, it, val] = bisezione(fun, 0, 3, tolx);

% input: funzione, intervallo di confidenza [a,b] e tolleranza
% output: radice approssimata, numero di iterazioni eseguite
%         e numero di valutazioni della funzione effettuate
function [radice, it, val] = bisezione(f, a, b, tolx)
    it=0;
    fa = feval(f, a);
    fb = feval(f, b);
    radice = (a+b)/2;
    fx = feval(f, radice);
    val = 3;
    imax = ceil(log2(b-a) - log2(tolx));
    if(fa*fb > 0)
        disp('ipotesi non verificata')
    else
        while(true)
            if (it>imax)
                break
            end

            it=it+1;
            f1x = abs((fb-fa)/(b-a)); %approx derivata prima

            if(abs(fx)<=tolx*f1x)
                break
            end

            if(fa*fx<0)
                b=radice;
                fb=fx;
            else
                a=radice;
                fa=fx;
            end

            radice = (a+b)/2;
            fx = feval(f, radice);
            val = val + 1;
        end
    end
end
