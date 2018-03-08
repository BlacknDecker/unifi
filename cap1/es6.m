Table = cell2table(cell(0,5));
Table.Properties.VariableNames = {'k' 'prima_succ' 'err_ass_prima_succ' 'seconda_succ' 'err_ass_seconda_succ' };

conergence = 1.732050807568877293527446;

for x = 0:8
    prima = first(x);
    seconda = second(x);
    e_a_prima = abs(prima - conergence);
    e_a_seconda = abs(seconda - conergence);
    iteration = {x, prima, e_a_prima, seconda, e_a_seconda};
    Table = [Table; iteration];
end

uitable('Data',Table{:,:},'ColumnName',Table.Properties.VariableNames,...
    'RowName',Table.Properties.RowNames,'Units', 'Normalized', 'Position',[0, 0, 1, 1]);

% definizione successioni
function y = first(k)
    if k == 0
        y = 3;
    else
        y = (first(k-1) + 3/first(k-1))/2;
    end
end

function y = second(k)
     if k == 0
        y = 3;
     else
         if k==1
             y = 2;
         else
             y = (3+second(k-2)*second(k-1))/(second(k-2) + second(k-1));
         end
     end
end
