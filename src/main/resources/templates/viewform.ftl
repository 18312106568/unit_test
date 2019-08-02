<el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
    <el-form ref="dataForm" :rules="rules" :model="temp" label-position="left" label-width="120px" style="width: 600px; margin-left:50px;">
        <#list bean.fieldNameList as field>
            <el-form-item :label="$t('datasource.${field}')">
                <el-input v-model="temp.${field}" type=""/>
            </el-form-item>
        </#list>
    </el-form>
    <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('table.cancel') }}</el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">{{ $t('table.confirm') }}</el-button>
    </div>
</el-dialog>
