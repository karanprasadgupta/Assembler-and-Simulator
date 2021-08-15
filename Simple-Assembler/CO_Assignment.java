import java.util.*;

public class CO_Assignment {
    public static ArrayList<ArrayList<ArrayList<String>>> input_from_console(){
        ArrayList<ArrayList<String>> input = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> input_for_error_generation = new ArrayList<ArrayList<String>>();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextLine()) {
            String read = sc.nextLine();
            //System.out.println(read == null || read.isEmpty() || read.equals(""));
            if (read == null || read.isEmpty()) {
                break;
            }
            //System.out.println(read);
            else if(read.trim()!="") {
                ArrayList<String> sub_input = new ArrayList<>(Arrays.asList(read.trim().split("\\s+")));
                if (sub_input.get(0).equals("var")) {
                    sub_input.add(0, "variable");
                } else if (sub_input.get(0).substring(sub_input.get(0).length() - 1).equals(":")) {
                    sub_input.add(0, "label");

                    ArrayList<String> temp_str=sub_input;
                    String s=sub_input.get(1).substring(0,sub_input.get(1).length()-1);
                    sub_input.remove(1);
                    sub_input.add(1,s);

                    //System.out.println(sub_input);
                    input.add(sub_input);
                    input_for_error_generation.add(sub_input);
                    continue;

                } else {
                    sub_input.add(0, "instruction");
                }
                //System.out.println(sub_input);
                input.add(sub_input);
                input_for_error_generation.add(sub_input);
            }

            else {
                ArrayList<String> sub_input = new ArrayList<>();
                //System.out.println(sub_input.size());
                input_for_error_generation.add(sub_input);
            }
        }
        ArrayList<ArrayList<ArrayList<String>>> input_arr=new ArrayList<ArrayList<ArrayList<String>>>();
        input_arr.add(input);
        input_arr.add(1,input_for_error_generation);
        return input_arr;
    }

    public static void Registers_Dict(){
        //register dictionary
        //Hashtable<String, String[]> registers = new Hashtable<String, String[]>();
        String[] R0={"000","0000000000000000"};
        globall.registers.put("R0",R0);
        String[] R1={"001","0000000000000000"};
        globall.registers.put("R1",R1);
        String[] R2={"010","0000000000000000"};
        globall.registers.put("R2",R2);
        String[] R3={"011","0000000000000000"};
        globall.registers.put("R3",R3);
        String[] R4={"100","0000000000000000"};
        globall.registers.put("R4",R4);
        String[] R5={"101","0000000000000000"};
        globall.registers.put("R5",R5);
        String[] R6={"110","0000000000000000"};
        globall.registers.put("R6",R6);
        String[] FLAGS={"111","0000000000000000"};
        globall.registers.put("FLAGS",FLAGS);

    }

    public static Hashtable<String, String[]> Opcode_Dict(){
        //opcode dictionary
        Hashtable<String, String[]> opcode = new Hashtable<String, String[]>();
        String[] add={"00000","A"};
        opcode.put("add",add);
        String[] sub={"00001","A"};
        opcode.put("sub",sub);
        String[] mul={"00110","A"};
        opcode.put("mul",mul);
        String[] div={"00111","C"};
        opcode.put("div",div);
        String[] xor={"01010","A"};
        opcode.put("xor",xor);
        String[] or={"01011","A"};
        opcode.put("or",or);
        String[] and={"01100","A"};
        opcode.put("and",and);
        String[] movimm={"00010","B"};
        opcode.put("mov1",movimm);
        String[] movreg={"00011","C"};
        opcode.put("mov2",movreg);
        String[] rs={"01000","B"};
        opcode.put("rs",rs);
        String[] ls={"01001","B"};
        opcode.put("ls",ls);
        String[] not={"01101","C"};
        opcode.put("not",not);
        String[] cmp={"01110","C"};
        opcode.put("cmp",cmp);
        String[] ld={"00100","D"};
        opcode.put("ld",ld);
        String[] st={"00101","D"};
        opcode.put("st",st);
        String[] jmp={"01111","E"};
        opcode.put("jmp",jmp);
        String[] jlt={"10000","E"};
        opcode.put("jlt",jlt);
        String[] jgt={"10001","E"};
        opcode.put("jgt",jgt);
        String[] je={"10010","E"};
        opcode.put("je",je);
        String[] hlt={"10011","F"};
        opcode.put("hlt",hlt);
        return opcode;
    }

    public static Hashtable<String, String> Label_memory_Dict(ArrayList<ArrayList<String>> input){
        //Label Dictionary Stores {label name : memory address
        Hashtable<String, String> label_dict = new Hashtable<String, String>();
        int memory_counter=0;
        for (int i=0;i<input.size();i++){
            if (input.get(i).get(0).equals("instruction"))
                memory_counter++;
            else if(input.get(i).get(0).equals("label")){
                String binaryresult = Integer.toBinaryString(memory_counter);
                String BinaryresultWithPadding = String.format("%8s", binaryresult).replaceAll(" ", "0");
                label_dict.put(input.get(i).get(1).substring(0,input.get(i).get(1).length()),BinaryresultWithPadding);
                memory_counter++;
            }
        }
        return label_dict;
    }

    public static Hashtable<String, String> variable_memory_assigner_Dict(ArrayList<ArrayList<String>> input){

        Hashtable<String, String> variable_dict = new Hashtable<String, String>();
        int memory_counter=0;
        for (int i=0;i<input.size();i++){
            if ((input.get(i).get(0).equals("instruction")) || (input.get(i).get(0).equals("label"))){
                memory_counter++;
            }
        }
        for (int i=0;i<input.size();i++){
            if (input.get(i).get(0).equals("variable")){
                String binaryresult = Integer.toBinaryString(memory_counter);
                String BinaryresultWithPadding = String.format("%8s", binaryresult).replaceAll(" ", "0");
                variable_dict.put(input.get(i).get(2),BinaryresultWithPadding);
                memory_counter++;
            }
            else
                break;
        }
        return variable_dict;
    }

    public static void input_to_machine_code(ArrayList<ArrayList<String>> input,Hashtable<String, String[]> opcode,Hashtable<String, String> variable_mem_dict,Hashtable<String, String> label_mem_dict){
        for (int i=0;i<input.size();i++){
            if(input.get(i).get(0).equals("instruction")){
                instruction_to_mcode(input.get(i),opcode,variable_mem_dict,label_mem_dict );
            }
            else if(input.get(i).get(0).equals("label")){
                ArrayList<String> label_instruction=new ArrayList<String>(input.get(i));
                label_instruction.remove(0);
                label_instruction.remove(0);
                label_instruction.add(0,"instruction");
                instruction_to_mcode(label_instruction,opcode,variable_mem_dict,label_mem_dict);
            }
        }
    }

    public static void instruction_to_mcode(ArrayList<String> instruction,Hashtable<String, String[]> opcode,Hashtable<String, String> variable_mem_dict,Hashtable<String, String> label_mem_dict){
        //System.out.println("Converting"+instruction.toString());
        if(instruction.get(1).equals("add") || instruction.get(1).equals("sub") || instruction.get(1).equals("mul") || instruction.get(1).equals("xor") || instruction.get(1).equals("or") || instruction.get(1).equals("and")){
            Type_A_out(instruction,opcode);
        }
        else if(instruction.size()==4 && isRegister(instruction.get(3)) && (instruction.get(1).equals("div") || instruction.get(1).equals("cmp") || instruction.get(1).equals("not") || instruction.get(1).equals("mov"))){
            Type_C_out(instruction,opcode);
        }
        else if((instruction.get(1).equals("ls") || instruction.get(1).equals("rs") || instruction.get(1).equals("mov"))){
            Type_B_out(instruction,opcode);
        }

        else if(instruction.get(1).equals("ld") || instruction.get(1).equals("st")){
            Type_D_out(instruction,opcode,variable_mem_dict);
        }
        else if(instruction.get(1).equals("jmp") || instruction.get(1).equals("jlt") || instruction.get(1).equals("jgt") || instruction.get(1).equals("je")){
            //System.out.println("running type e");
            Type_E_out(instruction,opcode,label_mem_dict);
        }
        else if (instruction.get(1).equals("hlt")){
            Type_F_out(instruction,opcode);
        }
    }

    public static boolean isRegister(String S){
        if(globall.registers.get(S)!=null)
            return true;
        return false;
    }

    public static String imm_to_binary(String S){
        String binaryresult = Integer.toBinaryString(Integer.parseInt(S));
        String BinaryresultWithPadding = String.format("%8s", binaryresult).replaceAll(" ", "0");
        return BinaryresultWithPadding;
    }

    public static void Type_A_out(ArrayList<String> instruction,Hashtable<String, String[]> opcode){
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("xor") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("xor")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("and") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("and")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("or") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("or")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("mul") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("mul")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("add") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("add")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("sub") && isRegister(instruction.get(2)) && isRegister(instruction.get(3)) && isRegister(instruction.get(4))){
            System.out.println(opcode.get("sub")[0]+"00"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]+""+ globall.registers.get(instruction.get(4))[0]);
        }
    }

    public static void Type_B_out(ArrayList<String> instruction,Hashtable<String, String[]> opcode){
        //left shift-ls
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("ls") && isRegister(instruction.get(2))){
            String imm_bool=imm_to_binary(instruction.get(3).substring(1));
            System.out.println(opcode.get("ls")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+imm_bool);
        }
        //right shift-rs
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("rs") && isRegister(instruction.get(2))){
            String imm_bool=imm_to_binary(instruction.get(3).substring(1));
            System.out.println(opcode.get("rs")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+imm_bool);
        }
        //move immediate-mov imm
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("mov") && isRegister(instruction.get(2))){
            String imm_bool=imm_to_binary(instruction.get(3).substring(1));
            System.out.println(opcode.get("mov1")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+imm_bool);
        }
    }

    public static void Type_C_out(ArrayList<String> instruction,Hashtable<String, String[]> opcode){
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("not") && isRegister(instruction.get(2)) && isRegister(instruction.get(3))){
            System.out.println(opcode.get("not")[0]+"00000"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("cmp") && isRegister(instruction.get(2)) && isRegister(instruction.get(3))){
            System.out.println(opcode.get("cmp")[0]+"00000"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("div") && isRegister(instruction.get(2)) && isRegister(instruction.get(3))){
            System.out.println(opcode.get("div")[0]+"00000"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]);
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("mov") && isRegister(instruction.get(2)) && isRegister(instruction.get(3))){
            System.out.println(opcode.get("mov2")[0]+"00000"+ globall.registers.get(instruction.get(2))[0]+""+ globall.registers.get(instruction.get(3))[0]);
        }
    }

    public static void Type_D_out(ArrayList<String> instruction,Hashtable<String,String[]> opcode,Hashtable<String, String> variable_mem_dict){
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("ld") && isRegister(instruction.get(2))){
            System.out.println(opcode.get("ld")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+ variable_mem_dict.get(instruction.get(3)));
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("st") && isRegister(instruction.get(2))){
            System.out.println(opcode.get("st")[0]+""+ globall.registers.get(instruction.get(2))[0]+""+ variable_mem_dict.get(instruction.get(3)));
        }
    }

    public static void Type_E_out(ArrayList<String> instruction,Hashtable<String,String[]> opcode,Hashtable<String, String> label_mem_dict){
        //System.out.println(instruction);
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("jmp")  && label_mem_dict.get(instruction.get(2))!=null) {
            System.out.println(opcode.get("jmp")[0]+"000"+ label_mem_dict.get(instruction.get(2)));
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("jlt")  && label_mem_dict.get(instruction.get(2))!=null){
            System.out.println(opcode.get("jlt")[0]+"000"+ label_mem_dict.get(instruction.get(2)));
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("jgt") && label_mem_dict.get(instruction.get(2))!=null){
            System.out.println(opcode.get("jgt")[0]+"000"+ label_mem_dict.get(instruction.get(2)));
        }
        else if(instruction.get(0).equals("instruction") && instruction.get(1).equals("je") && label_mem_dict.get(instruction.get(2))!=null){
            System.out.println(opcode.get("je")[0]+"000"+ label_mem_dict.get(instruction.get(2)));
        }
    }

    public static void Type_F_out(ArrayList<String> instruction,Hashtable<String, String[]> opcode){
        if(instruction.get(0).equals("instruction") && instruction.get(1).equals("hlt")){
            System.out.println(opcode.get("hlt")[0]+"00000000000");
        }
    }
    //MCODE PERFECTLY FINE UPTO HERE FOR ERROR FREE TESTS

    public static boolean check_code_is_error_free(ArrayList<ArrayList<String>> input,Hashtable<String, String[]> opcode,Hashtable<String, String> variable_mem_dict,Hashtable<String, String> label_mem_dict){
        boolean retvalue=true;
        if(!variable_at_begin_validator(input))
            retvalue=false;
        //System.out.println("Success : "+1);
        if(!is_variable_check(input))
            retvalue=false;
        //System.out.println("Success : "+2);
        if(!var_lab_not_same(input,variable_mem_dict,label_mem_dict))
            retvalue=false;
        //System.out.println("Success : "+3);
        if(!hlt_checker(input))
            retvalue=false;
        //System.out.println("Success : "+4);
        if (!is_label_check(input))
            retvalue=false;
        //System.out.println("Success : "+5);
        if (!instructions_validator(input,opcode,variable_mem_dict,label_mem_dict))
            retvalue=false;
        //System.out.println("Success : "+6);
        return retvalue;
    }

    public static boolean instructions_validator(ArrayList<ArrayList<String>> input,Hashtable<String, String[]> opcode,Hashtable<String, String> variable_mem_dict,Hashtable<String, String> label_mem_dict){
        boolean ret_value=true;
        for (int i=0;i<input.size();i++){
            if(input.get(i).size()==0)
                continue;
            if(input.get(i).get(0).equals("instruction")){
                if(!instruction_syntax_checker(input.get(i),opcode,variable_mem_dict,label_mem_dict,i+1))
                    ret_value=false;
            }
            else if(input.get(i).get(0).equals("label")){
                ArrayList<String> label_instruction=new ArrayList<String>(input.get(i));
                label_instruction.remove(0);
                label_instruction.remove(0);
                label_instruction.add(0,"instruction");
                if(label_instruction.size()==1){
                    System.out.println("Error in Line "+(i+1)+" :Label doesn't have any instruction");
                    continue;
                }
                if(!instruction_syntax_checker(label_instruction,opcode,variable_mem_dict,label_mem_dict,i+1))
                    ret_value=false;
            }
        }
        return ret_value;
    }

    public static boolean instruction_syntax_checker(ArrayList<String> instruction,Hashtable<String, String[]> opcode,Hashtable<String, String> variable_mem_dict,Hashtable<String, String> label_mem_dict,int line_no){
        //System.out.println("Converting"+instruction.toString());
        if(instruction.get(1).equals("add") || instruction.get(1).equals("sub") || instruction.get(1).equals("mul") || instruction.get(1).equals("xor") || instruction.get(1).equals("or") || instruction.get(1).equals("and")){
            return type_A_syntex_check(instruction,line_no);
        }
        else if(instruction.get(1).equals("ls") || instruction.get(1).equals("rs")){
            return type_B_syntex_check(instruction,line_no);
        }
        else if(instruction.get(1).equals("div") || instruction.get(1).equals("cmp") || instruction.get(1).equals("not")){
            return type_C_syntex_check(instruction,line_no);
        }
        else if(instruction.get(1).equals("ld") || instruction.get(1).equals("st")){
            return type_D_syntex_check(instruction,variable_mem_dict,label_mem_dict,line_no);
        }
        else if(instruction.get(1).equals("jmp") || instruction.get(1).equals("jlt") || instruction.get(1).equals("jgt") || instruction.get(1).equals("je")){
            return type_E_syntex_checkE(instruction,variable_mem_dict,label_mem_dict,line_no);
        }
        else if (instruction.get(1).equals("hlt")){
            return type_F_syntex_check(instruction,line_no);
        }
        else if (instruction.get(1).equals("mov")){
            return mov_syntex_check(instruction,line_no);
        }
        else {
            System.out.println("Error in Line "+line_no+" :There is typo in instruction name \'"+instruction.get(1)+"\' is not a valid instruction");
            return false;
        }
    }

    public static boolean variable_at_begin_validator(ArrayList<ArrayList<String>> input){
        //A variable definition is of the following format: var xyz (done)
        //which declares a 16 bit variable called xyz. This variable name can be used in place of mem_addr fields in load and store instructions.
        //All variables must be defined at the very beginning of the assembly program. (done)
        //A variable name consists of alphanumeric characters and underscores. (done)
        int variables_at_beg=0;
        int instruction_count=0;
        int label_count=0;
        int variabe_count=0;
        for (int i=0;i<input.size();i++){
            if(input.get(i).size()==0)
                continue;
            if (input.get(i).get(0).equals("variable") && instruction_count==0 && label_count==0)
                variables_at_beg++;
            if (input.get(i).get(0).equals("variable")){
                variabe_count++;

            }
            else if (input.get(i).get(0).equals("instruction"))
                instruction_count++;
            else if (input.get(i).get(0).equals("label"))
                label_count++;

            if (variabe_count!=variables_at_beg){
                System.out.println("Error in Line "+(i+1)+" : All Variables not defined at beginning");
                return false;
            }
        }
        return true;

    }

    public static boolean is_variable_check(ArrayList<ArrayList<String>> aList) {
        int i=0;
        ArrayList<String> temp = new ArrayList<>();
        int k = aList.size();
        boolean ret=true;
        while(i<k) {
            if(aList.get(i).size()==0) {
                i++;
                continue;
            }
            if(aList.get(i).get(0).equals("variable")) {
                if(aList.get(i).size()!=3) {
                    System.out.println("Error in Line "+(i+1)+" :Not valid : Can't declare more than 1 variable simultaneously");
                    ret= false;
                }
                else if(!aList.get(i).get(1).equals("var")) {
                    System.out.println("Error in Line "+(i+1)+" : Not valid : Enter valid argument only");
                    ret= false;
                }
                else {
                    String name = aList.get(i).get(2);
                    for(int j=0;j<name.length();j++) {
                        if(!(name.charAt(j)=='_'||Character.isLetterOrDigit(name.charAt(j)))) {
                            System.out.println("Error in Line "+(i+1)+" :Not valid : variable should only have AlphaNumeric chracters");
                            ret= false;
                            break;
                        }
                    }
                    if(temp.contains(name)) {
                        System.out.println("Error in Line "+(i+1)+" :Not valid : Can't declare same variable multiple times");
                        ret= false;
                    }
                    else
                        temp.add(name);
                }
            }
            i++;
        }
        return ret;
    }

    public static boolean is_valid_immidiate_boolean(String s) {
        if(s.equals("")) {
            return false;
        }
        else if(s.charAt(0)!=('$')) {
            return false;
        }
        else {
            String b="";
            for(int i=1; i<s.length(); i++)
            {
                if(Character.isDigit(s.charAt(i))) {
                    b=b+s.charAt(i);
                }else {
                    return false;
                }}
            if(b.equals("")) {
                return false;
            }
            int val = Integer.parseInt(b);
            if(val>=0&&val<=255) {
                return true;
            }else {
                return false;
            }
        }
    }

    public static boolean var_lab_not_same(ArrayList<ArrayList<String>> input,Hashtable<String,String> variable, Hashtable<String,String> lab) {
        boolean ret=true;
        for(String key1:variable.keySet()){
            if(variable.get(key1)!=null && lab.get(key1)!=null){
                for (int i=0;i<input.size();i++){
                    if(input.get(i).get(0).equals("label") && input.get(i).get(1).equals(key1)){
                        System.out.println("Error in Line :"+(i+1)+" Variables and Labels are being declared with same name");
                    }
                }

                ret=false;
            }
        }
        return ret;
    }

    public static boolean hlt_checker(ArrayList<ArrayList<String>> aList) {
        int i=0;
        int last=0;
        int times =0;
        int k = aList.size();
        while(i<k) {
            if(aList.get(i).size()==0) {
                i++;
            }
            else {
                if(aList.get(i).get(0).equals("instruction")) {
                    if(aList.get(i).size()==2 && aList.get(i).get(1).equals("hlt")) {
                        last=1;
                        times+=1;
                        if(times>1)
                            break;
                    }else {
                        last=0;
                    }
                }else if(aList.get(i).get(0).equals("label")) {
                    if(aList.get(i).size()==3 && aList.get(i).get(2).equals("hlt")) {
                        last=1;
                        times+=1;
                        if(times>1)
                            break;
                    }else {
                        last=0;
                    }
                }else {
                    last=0;
                }
                i++;
            }
        }
        boolean valid = (times==1 && last==1);
        if (valid==false){
            if(times>1)
                System.out.println("Error in Line "+i+" : Hlt can only be used once");
            else if(times==0)
                System.out.println("Error in Line "+k+" : Missing hlt instruction");
            else if(last==0)
                System.out.println("Error in Line "+k+" : hlt is not being used as the last instruction");

        }
        return valid;
    }

    public static boolean is_label_check(ArrayList<ArrayList<String>> aList) {
        int i=0;
        ArrayList<String> temp = new ArrayList<>();
        int k = aList.size();
        boolean ret=true;
        while(i<k) {
            if(aList.get(i).size()==0) {
                i++;
                continue;
            }

            else if(aList.get(i).get(0).equals("label") && aList.get(i).size()<=2){
                ret=false;
                System.out.println("Error in Line "+(i+1)+" :label has no instruction");
            }
            if(aList.get(i).get(0).equals("label") )
            {
                String name=aList.get(i).get(1);
                //System.out.println(name);
                if(name.matches("^[a-zA-Z0-9_]*$"))
                {
                    if(temp.size()==0)
                    {
                        temp.add(name);
                    }
                    else if(temp.contains(name))
                    {
                        System.out.println("Error in Line "+(i+1)+" :Label being declared again with same name not allowed");
                        ret=false;
                        i++;continue;
                    }
                    else if(!temp.contains(name))
                    {
                        temp.add(name);
                    }
                }
                else
                {
                    System.out.println("Error in Line "+(i+1)+" :Only alphanumeric names are allowed for labels");
                    ret=false;
                    i++;
                    continue;
                }
            }
            i++;


        }

        return ret;
    }

    public static boolean type_A_syntex_check(ArrayList<String> instruction,int line_no) {
        if(instruction.size()==5) {
            if(isRegister(instruction.get(2)) && !instruction.get(2).equals("FLAGS")){
                if(isRegister(instruction.get(3)) && !instruction.get(3).equals("FLAGS")) {
                    if(isRegister(instruction.get(4)) && !instruction.get(4).equals("FLAGS")) {
                        return true;
                    }
                    else if(isRegister(instruction.get(4)) && instruction.get(4).equals("FLAGS")) {
                        System.out.println("Error in Line "+line_no+" :Illegal use of FLAGS register");
                        return false;
                    }
                    else {
                        System.out.println("Error in Line "+line_no+" :"+instruction.get(4) + " is not a valid register");
                        return false;
                    }
                }
                else if(isRegister(instruction.get(3)) && instruction.get(3).equals("FLAGS")) {
                    System.out.println("Error in Line "+line_no+" :Illegal use of FLAGS register");
                    return false;
                }
                else {
                    System.out.println("Error in Line "+line_no+" :"+instruction.get(3) + " is not a valid register");
                    return false;
                }
            }
            else if(isRegister(instruction.get(2)) && instruction.get(2).equals("FLAGS")) {
                System.out.println("Error in Line "+line_no+" :Illegal use of FLAGS register");
                return false;
            }
            else {
                System.out.println("Error in Line "+line_no+" :"+instruction.get(2) + " is not a valid register");
                return false;
            }
        }
        else {
            if(instruction.size()>5) {
                System.out.println("Error in Line "+line_no+" :Not valid: This type of instruction should not have more than 3 registers as argument");
                return false;
            }else {
                System.out.println("Error in Line "+line_no+" :Not valid: This type of instruction should not have less than 3 registers as argument");
                return false;
            }
        }
    }

    public static boolean type_B_syntex_check(ArrayList<String> instruction,int line_no) {
        if(instruction.size()==4){
            if(isRegister(instruction.get(2)) && !instruction.get(2).equals("FLAGS")) {
                if(is_valid_immidiate_boolean(instruction.get(3))) {
                    return true;
                }
                else {
                    System.out.println("Error in Line "+line_no+" :"+instruction.get(3)+" is not a valid immediate value");
                    return false;
                }
            }
            else {
                if(instruction.get(2).equals("FLAGS")){
                    System.out.println("Error in Line "+line_no+" :Illegal use of FLAGS register");
                    return false;
                }else {
                    System.out.println("Error in Line "+line_no+" :"+instruction.get(2) + " is not a valid register");
                    return false;
                }
            }
        }else {
            if(instruction.size()>4) {
                System.out.println("Error in Line "+line_no+" :Not valid: This type of instruction should not have more than 1 register and 1 immidiate argument");
                return false;
            }else {
                System.out.println("Error in Line "+line_no+" :Not valid: This type of instruction should not have less than 1 register and 1 immidiate value as argument");
                return false;
            }
        }
    }

    public static boolean type_C_syntex_check(ArrayList<String> instruction,int line_no) {
        if(instruction.size()==4) {
            if(isRegister(instruction.get(2)) && !instruction.get(2).equals("FLAGS")){
                if(isRegister(instruction.get(3)) && !instruction.get(3).equals("FLAGS")) {
                    return true;
                }
                else {
                    if(instruction.get(3).equals("FLAGS")){
                        System.out.println("Error in Line "+line_no+" :Illegal use of FLAGS register");
                        return false;
                    }
                    else {
                        System.out.println("Error in Line "+line_no+" :"+instruction.get(3)+ " is not a valid register");
                        return false;
                    }
                }
            }
            else {
                if(instruction.get(2).equals("FLAGS")){
                    System.out.println("Error in Line "+line_no+" :Illegal use of FLAGS register");
                    return false;
                }else {
                    System.out.println("Error in Line "+line_no+" :"+instruction.get(2)+ " is not a valid register");
                    return false;
                }
            }
        }
        else {
            if(instruction.size()>4) {
                System.out.println("Error in Line "+line_no+" :Not valid: This type of instruction should not have more than 2 registers as argument");
                return false;
            }
            else {
                System.out.println("Error in Line "+line_no+" :Not valid: This type of instruction should not have less than 2 registers as argument");
                return false;
            }
        }
    }

    public static boolean type_D_syntex_check(ArrayList<String> instruction, Hashtable<String,String> variable, Hashtable<String,String> label,int line_no) {
        if(instruction.size()==4) {
            if(isRegister(instruction.get(2)) && !instruction.get(2).equals("FLAGS")) {
                if(variable.containsKey(instruction.get(3))&&!label.containsKey(instruction.get(3))) {
                    return true;
                }
                else {
                    if(variable.containsKey(instruction.get(3))&&label.containsKey(instruction.get(3))) {
                        System.out.println("Error in Line "+line_no+" :You cant have same name for variable and a label");
                        return false;
                    }
                    else if(!variable.containsKey(instruction.get(3))&&label.containsKey(instruction.get(3))) {
                        System.out.println("Error in Line "+line_no+" :Type mismatch: You are misusing label as variable");
                        return false;
                    }
                    else {
                        System.out.println("Error in Line "+line_no+" :No variable named "+instruction.get(3)+" found");
                        return false;
                    }
                }
            }
            else {
                if(instruction.get(2).equals("FLAGS")) {
                    System.out.println("Error in Line "+line_no+" :Illegal use of FLAGS register");
                    return false;
                }else {
                    System.out.println("Error in Line "+line_no+" :Not valid: "+instruction.get(2)+" is not a register");
                    return false;
                }
            }
        }else {
            if(instruction.size()>4) {
                System.out.println("Error in Line "+line_no+" :Not valid: This instruction should not have more than one register and  one variable");
                return false;
            }else {
                System.out.println("Error in Line "+line_no+" :Not valid: This instruction should have one register and one variable ");
                return false;
            }
        }

    }

    public static boolean type_E_syntex_checkE(ArrayList<String> instruction, Hashtable<String,String> var, Hashtable<String,String> lab,int line_no) {
        int b=0;
        if(instruction.size()==3)
        {
            if(lab.get(instruction.get(2))!=null) {
                    b=1;
                    return true;
            }
            if(var.get(instruction.get(2))!=null){
                    System.out.println("Error in Line "+line_no+" :variable is being misused as label");
                return false;
            }
            System.out.println("Error in Line "+line_no+" :"+instruction.get(2)+"is not a valid label");
            return false;

        }
        else
        {
            System.out.println("Error in Line "+line_no+" :Inavlid syntax for jump instruction");
            return false;
        }
    }

    public static boolean type_F_syntex_check(ArrayList<String> instruction,int line_no) {
        if(instruction.size()==2) {
            return true;
        }else {
            System.out.println("Error in Line "+line_no+" :Not valid hlt instruction");
            return false;
        }
    }

    public static boolean mov_syntex_check(ArrayList<String> instruction,int line_no){
        if(instruction.size()==4){
            if(isRegister(instruction.get(2)) && !instruction.get(2).equals("FLAGS")) {
                if(is_valid_immidiate_boolean(instruction.get(3)) || isRegister(instruction.get(3))) {
                    return true;
                }
                else {
                    System.out.println("Error in Line "+line_no+" :"+instruction.get(3)+" is not a valid register/immediate value");
                    return false;
                }

            }
            else {
                if(instruction.get(2).equals("FLAGS")){
                    System.out.println("Error in Line "+line_no+" :Illegal use of FLAGS register");
                    return false;
                }else {
                    System.out.println("Error in Line "+line_no+" :"+instruction.get(2) + " is not a valid register");
                    return false;
                }
            }
        }
        else {
                System.out.println("Error in Line "+line_no+" :Not valid: This type of instruction should have either 2 registers or 1 register and 1 immediate value ");
                return false;
            }
        }

    public static void main(String[] args){
        ArrayList<ArrayList<ArrayList<String>>> input_arr=input_from_console();
        ArrayList<ArrayList<String>> input_for_machine_code_generation = input_arr.get(0);
        ArrayList<ArrayList<String>> input_for_error_generation = input_arr.get(1);
        Registers_Dict();
        Hashtable<String, String[]> Opcode_dict = Opcode_Dict();
        Hashtable<String, String> Label_dict = Label_memory_Dict(input_for_machine_code_generation);
        Hashtable<String, String> variable_memory_dict=variable_memory_assigner_Dict(input_for_machine_code_generation);

        boolean error_free=check_code_is_error_free(input_for_error_generation,Opcode_dict,variable_memory_dict,Label_dict);
        if(error_free){
            input_to_machine_code(input_for_machine_code_generation,Opcode_dict,variable_memory_dict,Label_dict);
        }
    }
}
class globall{
    public static Hashtable<String, String[]> registers = new Hashtable<String, String[]>();
}
